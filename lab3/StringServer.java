import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>(); 

    public String getList() {
        String str = "";
        for(int i = 0; i < strings.size(); i++) {
            str += (i+1) + ". " + strings.get(i) + "\n";
        }
        return str;
    }

    public String handleRequest(URI url) {
        if (url.getPath().contains("/add-message")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings.add(parameters[1]);
                return String.format(getList());
            }
        }
        else if (url.getPath().contains("/")) {
            String str = getList();
            if(str.length() < 1) str = "No strings added yet.";
            return String.format(str);
        }
        return "404 Not Found!";
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
