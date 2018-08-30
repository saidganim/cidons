

import javafx.util.Pair;

import java.util.ArrayList;

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

    public static void main(String[] args) {
        Config[] config = new Config[10];
        for(int i = 0 ; i < 10; ++i)
            config[i] = new Config();
        System.out.println(config[0]);
        config[1].port = 1111;
        config[1].initiator = true;
        config[2].port = 1112;
        config[3].port = 1113;
        config[4].port = 1114;
        config[5].port = 1115;
        config[6].port = 1116;
        config[7].port = 1117;
        config[8].port = 1118;
        config[9].port = 1119;

        config[1].id = 1;
        config[2].id = 2;
        config[3].id = 3;
        config[4].id = 4;
        config[5].id = 5;
        config[6].id = 6;
        config[7].id = 7;
        config[8].id = 8;
        config[9].id = 9;

        config[1].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1112),2));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1116),6));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1117),7));
        }};
        config[2].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
        }};
        config[3].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1119),9));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1118),8));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1117),7));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
        }};
        config[4].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
        }};
        config[5].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1114),4));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1119),9));
        }};
        config[6].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
        }};
        config[7].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1111),1));
        }};
        config[8].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
        }};
        config[9].links = new ArrayList<NodeInstance>(){{
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1113),3));
            add(new NodeInstance(new Pair<String, Integer>("127.0.0.1", 1115),5));
        }};
        ArrayList<Runnable> fs = new ArrayList<Runnable>();

        for(int i = 0; i < 9; ++i){
            final Config conf = config[i + 1];
             fs.add(() -> {
                Node node = new Node(conf);
            });
        }

        for(Runnable curr : fs){
            new Thread(curr).start();
        }
    }
}
