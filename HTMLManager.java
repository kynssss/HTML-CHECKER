import java.util.*;

public class HTMLManager {
   private Queue<HTMLTag> tags;
   
   // this method is the constructor that takes a queue of HTMLTags, throws IllegalArgumentException if null,
   // and copies all tags into a new queue 
   public HTMLManager(Queue<HTMLTag> html) {
      if (html == null) {
         throw new IllegalArgumentException("nothing");
      }
      this.tags = new LinkedList<HTMLTag>();
      for (HTMLTag tag : html) {
            this.tags.add(tag);
      }
   }

   // this method returns the queue of HTMLTags currently being managed
   public Queue<HTMLTag> getTags() {
      return tags;
   }

   // this method returns a string of all tags in the queue without changing the queue's state
   public String toString() {
      String result = "";
      int size = tags.size();
      for (int i = 0; i < size; i++) {
         HTMLTag val = tags.remove();
         result += val.toString().trim();
         tags.add(val);
      }
      return result;
   }

   // this method fixes the wrong HTML by processing each tag using a stack to track unmatched
   // opening tags, correcting mismatched closing tags, and closing leftovers that arent matched yet
   public void fixHTML() {
      Stack<HTMLTag> stack = new Stack<HTMLTag>();
      Queue<HTMLTag> fixed = new LinkedList<HTMLTag>();
      int size = tags.size();

      for (int i = 0; i < size; i++) {
         HTMLTag current = tags.remove();
         if (current.isSelfClosing()) {
            fixed.add(current);
         } else if (current.isOpening()) {
            fixed.add(current);
            stack.push(current);
         } else if (current.isClosing()) {
            if (!stack.isEmpty() && stack.peek().matches(current)) {
            fixed.add(current);
            stack.pop();
         } else if (!stack.isEmpty() && !stack.peek().matches(current)) {
            fixed.add(stack.pop().getMatching());
            tags.add(current);
            size++;
            }
         }
      }

      // Close any leftover unclosed opening tags still on the stack
      while (!stack.isEmpty()) {
         fixed.add(stack.pop().getMatching());
      }
      
      this.tags = fixed;
    }
}

/* PROGRAM OUTPUT: 
----jGRASP exec: java -ea HTMLChecker
===============================
Processing tests/test3.html...
===============================
HTML: <br /></p></p>
Checking HTML for errors...
HTML after fix: <br />
----> Result matches Expected Output!

===============================
Processing tests/test2.html...
===============================
HTML: <a><a><a></a>
Checking HTML for errors...
HTML after fix: <a><a><a></a></a></a>
----> Result matches Expected Output!

===============================
Processing tests/test5.html...
===============================
HTML: <div><h1></h1><div><img /><p><br /><br /><br /></div></div></table>
Checking HTML for errors...
HTML after fix: <div><h1></h1><div><img /><p><br /><br /><br /></p></div></div>
----> Result matches Expected Output!

===============================
Processing tests/test4.html...
===============================
HTML: <div><div><ul><li></li><li></li><li></ul></div>
Checking HTML for errors...
HTML after fix: <div><div><ul><li></li><li></li><li></li></ul></div></div>
----> Result matches Expected Output!

===============================
Processing tests/test1.html...
===============================
HTML: <b><i><br /></b></i>
Checking HTML for errors...
HTML after fix: <b><i><br /></i></b>
----> Result matches Expected Output!

===============================
        All tests passed!
===============================

 ----jGRASP: Operation complete. 
*/
