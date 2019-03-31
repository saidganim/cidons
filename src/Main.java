



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Main {

    /*
        (1111) 1: 2, 3, 6, 7
        (1112) 2: 1
        (1113) 3: 1, 9, 8, 7, 4
        (1114) 4: 3, 5
        (1115) 5: 4, 9
        (1116) 6: 1
        (1117) 7: 3, 1
        (1118) 8: 3
        (1119) 9: 3, 5

     */

    public static Integer counter = 0;
    public static Long start;
    private static int NODENUM = 3;
    private static int PROBABILITY = 100; // percentage

//    private static Config[] config_generator(){
//        Config[] configs = new Config[NODENUM];
//        for(int i = 0 ; i < NODENUM; ++i)
//            configs[i] = new Config();
//        configs[0].initiator = true;
//
//        // Generating links for nodes
//
//        NodeInstance[] nodeInstances = new NodeInstance[NODENUM];
//        int port = 1111;
//        for(int i = 0; i < NODENUM; ++i){
//            nodeInstances[i] = new NodeInstance(new Pair<String, Integer>("127.0.0.1", port++),i + 1);
//        }
//        Random rand = new Random();
//        for(int i = 0; i < NODENUM; ++i){
//            configs[i].links = new ArrayList<NodeInstance>();
//            for(int j = 0; j <= i; ++j){
//
//                if(j == i || rand.nextInt(100) > PROBABILITY)
//                    continue;
//                configs[i].links.add(nodeInstances[j]);
//                configs[j].links.add(nodeInstances[i]);
//            }
//        }
//
//        for(int i = 0; i < NODENUM; ++i)
//                System.out.println(" CONFIG " +  i + " is sent " + configs[i].links);
//        return configs;
//    }


    private static Config[] config_generator(){
        // 10.141.0.49, 10.141.0.50, 10.141.0.51
        InetAddress ip = null;
        String hostname;

        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }

        Config[] result = new Config[1];
        result[0] = new Config();

        if(ip.toString().equals("10.141.0.49")){
            result[0].initiator = true;
            result[0].links = new ArrayList<NodeInstance>(){{
                add(new NodeInstance(new Pair<String, Integer>("10.141.0.50", 1111),2));
                add(new NodeInstance(new Pair<String, Integer>("10.141.0.51", 1119),3));
            }};
        } else if(ip.toString().equals("10.141.0.50")){
            result[0].links = new ArrayList<NodeInstance>(){{
                add(new NodeInstance(new Pair<String, Integer>("10.141.0.49", 1111),1));
                add(new NodeInstance(new Pair<String, Integer>("10.141.0.51", 1119),3));
            }};
        } else {
            result[0].links = new ArrayList<NodeInstance>(){{
                add(new NodeInstance(new Pair<String, Integer>("10.141.0.49", 1111),1));
                add(new NodeInstance(new Pair<String, Integer>("10.141.0.50", 1119),2));
            }};
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        Config[] configs = config_generator();
        //============================================================= First configuration ========================================================
//        configs[0].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1116),6));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1117),7));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1118),8));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1119),9));
//        }};
//        configs[1].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[2].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[3].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[4].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[5].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[6].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[7].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[8].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};

//        configs[0].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1116),6));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1117),7));
//        }};
//        configs[1].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[2].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1119),9));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1118),8));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1117),7));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//        }};
//        configs[3].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//        }};
//        configs[4].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1119),9));
//        }};
//        configs[5].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[6].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//        }};
//        configs[7].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//        }};
//        configs[8].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//        }};


        // =========================================================================
//// CLIQUE
//
//        configs[0].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//        }};
//
//        configs[1].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//        }};
//
//        configs[2].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//        }};
//
//        configs[3].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
//        }};
//
//        configs[4].links = new ArrayList<NodeInstance>(){{
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
//            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
//        }};

        ArrayList<Runnable> fs = new ArrayList<Runnable>();

        for(int i = 0; i < configs.length; ++i){
            final Config conf = configs[i];
             fs.add(() -> {
                Node node = new Node(conf);
            });
        }
        start = System.currentTimeMillis();
        for(Runnable curr : fs){
            new Thread(curr).start();
        }
    }
}
