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
  
  public String toString() {
     String result = "";
     int size = tags.size();
     for(int i = 0; i < size; i++) {
        HTMLTag val = tags.remove();
        result += tags.toString().trim();
        tags.add(val);
     }
     return result;
  }

  public void fixHTML() {
    Stack<HTMLTag> stack = new Stack<HTMLTag>();
    ueue<HTMLTag> fixed = new LinkedList<HTMLTag>();
    int size = tags.size();
 
    for (int i = 0; i < size; i++) {
      HTMLTag current = tags.remove();
      if (current.isSelfClosing()) {
        fixed.add(current);
      } else if (current.isOpening()) {
        fixed.add(current);
        stack.push(current);
      } else if (current.isClosing()) {
        // Closing tag, check if it matches the top of the stack
        if (!stack.isEmpty() && stack.peek().matches(current)) {
          fixed.add(current);
          stack.pop();
        } else if (!stack.isEmpty() && !stack.peek().matches(current)) {
          // Wrong closing tag, add the correct closing tag for the top opener
          fixed.add(stack.pop().getMatching());
          tags.add(current);
          size++; 
        }
      }
    }
  } 

  while (!stack.isEmpty()) {
      fixed.add(stack.pop().getMatching());
  }
}
