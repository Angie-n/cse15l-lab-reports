import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return stringList.toString();
        } 
        else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                stringList.add(parameters[1]);
                return String.format("%s added to list", parameters[1]);
            }
        }
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                ArrayList<String> matches = new ArrayList<String>();
                for(int i = 0; i < stringList.size(); i++) {
                    if(stringList.get(i).contains(parameters[1])) matches.add(stringList.get(i));
                }
                return matches.toString();
            }
        }
        return "404 Not Found!";
    }
}

class SearchServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
