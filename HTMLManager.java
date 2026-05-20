import java.util.*;

public class HTMLManager {
  private Queue<HTMLTag> tags;
  
  public HTMLManager(Queue<HTMLTag> html){
     if(html == null) {
        throw new IllegalArgumentException("nothing");
     } else {
        while(!html.isEmpty()){
           tags.add(html.remove());
        }
     }
  }
  public Queue<HTMLTag> getTags() {
     return tags;
  }
}
