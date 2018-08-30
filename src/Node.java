import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Node {

    private NetworkManager networkManager = null;
    private boolean initiator;
    private ArrayList<NodeInstance> links;
    private ArrayList<NodeInstance> toHandle;
    private ArrayList<NodeInstance> toNotify;
    private Set<NodeInstance> children = new HashSet<NodeInstance>();
    private NodeInstance father;
    private boolean started = false;
    public int _id;
    private boolean deactivated = false;

    public Node(Config config){
        this.networkManager = new NetworkManager(config.port);
        this.initiator = config.initiator;
        this.links = config.links;
        _id = config.id;
        this.toHandle = (ArrayList<NodeInstance>)links.clone();
        this.toNotify = (ArrayList<NodeInstance>)links.clone();
        this.networkManager.setNodesList(this.links);
        this.networkManager.id = _id;
        this.networkManager.init(this);
        try {
            Runnable f = () -> {
                try {
                    this.networkManager.run();
                } catch (IOException e) {
                }
            };
            new Thread(f).start(); // starting listener in separate thread;
            if(config.initiator) {
                Thread.sleep(1000);
                moveOn();
            } else {}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private NodeInstance getChildren(NodeInstance child){
        for(NodeInstance cur : children){
            if(child.equals(cur))
                return cur;
        }
        return null;
    }

    public NodeInstance getLink(NodeInstance child){
        for(NodeInstance cur : links){
            if(child.equals(cur))
                return cur;
        }
        return null;
    }


    private NodeInstance getFromToHandle(NodeInstance child){
        for(NodeInstance cur : toHandle){
            if(child.equals(cur))
                return cur;
        }
        return null;
    }

    private void moveOn(){

        if(toHandle.size() == 0){
            // Very good; we have finished exploring the neighbors. It's time to return the token to parent node
            deactivate();

        } else {
            // Still have some work to do
            NodeInstance nextNode = toHandle.remove(0);
            toNotify.remove(nextNode);
            _Message mess = new _Message();
            mess.messageType = _Message.MessageType.tokenf2s;
            children.add(nextNode);
            this.networkManager.sendMessage(nextNode, mess);
        }

        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!started){
            started = true;
            _Message visited = new _Message();
            visited.messageType = _Message.MessageType.visited;
            for(NodeInstance curr : toNotify) { // Notify all neighbors that I am visited only for once
                networkManager.sendMessage(curr, visited);
            }
        }

    }

    private void deactivate(){
        // Return Token to parent node
        if(deactivated)
            return;
        deactivated = true;
        NodeInstance nextNode = father;
        _Message mess = new _Message();
        mess.messageType = _Message.MessageType.tokens2f;
        StringBuilder builder = new StringBuilder("DEACTIVATE-" + _id + " :: ");
        for(NodeInstance curr : children)
            builder.append("child : " + curr.addr.getValue() + ";");
        System.out.println(builder.toString());
        if(father != null)
            this.networkManager.sendMessage(nextNode, mess);
        else {
            System.out.println("INITIATOR FINISHED THE TASK : " + Main.counter);
            System.exit(0);
        }
    }

    public _Message handleMessage(_Message mess){
        NodeInstance sender = getLink(mess.sender);

        switch(mess.messageType){
            case tokens2f:
//                System.out.println("NODE-" + this._id + " RECEIVED " + mess.toString());
                // Child explored all neighbor nodes and definitely attached as tree child
                moveOn();
                break;
            case tokenf2s:
//                System.out.println("NODE-" + this._id + " RECEIVED " + mess.toString());
                // Trying to explore new node and attach it to dfs-tree
                if(toHandle.indexOf(sender) != -1) // Remove from toHandle list if it still there
                    toHandle.remove(toHandle.indexOf(sender));
                if(father == null && sender == null){
                    System.out.println("Something is wrong. It shouldn't be the case");
                    System.exit(1);
                }
                if(father == null){
                    System.out.println("MOVEON");
                    father = sender;
                    toHandle.remove(sender);
                    children.remove(sender);
                    toNotify.remove(sender);
                    moveOn();
                }
                // Else Ignore the message because of eventually sender will receipt VISITED message
                // there is no break statement here on purpose...
                break;
            case visited:
//                System.out.println("NODE-" + this._id + " RECEIVED " +  mess.toString());
                // Current node already attached to some other node in dfs-tree
                    // We have to move on further
                toHandle.remove(sender);
                if(children.contains(sender)){
                    children.remove(sender);
                    moveOn();
                }

                break;
        }

        return null;
    }

}
