package schedulermem;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue; 
import java.util.Iterator;

/**
 * @author [Sam Sixta]
 */
public class MemoryScheduler {

    private int pageFaultCount;
    private int[] pages;

    public MemoryScheduler(int frames) {
        this.pageFaultCount = 0;
    }

    public int getPageFaultCount() {
        return this.pageFaultCount;
    }

    public void useFIFO(String referenceString) {
        HashSet<Integer> s = new HashSet<>(cap); 
        Queue<Integer> index = new LinkedList<>() ; 
        for (int i=0; i< referenceString.length(); i++) 
        { 
            if (s.size() < cap) 
            { 
                if (!s.contains(pages[i])) 
                { 
                    s.add(pages[i]); 
                    this.pageFaultCount++; 
                    index.add(pages[i]); 
                } 
            } 
            else
            { 
                if (!s.contains(pages[i])) 
                { 
                    int val = index.peek(); 
                    index.poll(); 
                    s.remove(val); 
                    s.add(pages[i]); 
                    index.add(pages[i]); 
                    this.pageFaultCount++;
                }
            }
        }
    }

    public void useOPT(String referenceString) {
        String[] opt_number = referenceString.split(",");
        pages = new int[opt_number.length];
        for(int i=0; i < opt_number.length; i++) {
            pages[i] = Integer.parseInt(opt_number[i]);
        }
        int frames = 3;
        int pointer = 0;
        int page_faults = 0;
        int ref;
        boolean isFull = false;
        int buffer[];
        boolean hit[];
        int fault[];
        int layout[][];
        ref = pages.length;
        layout = new int[ref][frames];
        buffer = new int[frames];
        hit = new boolean[ref];
        fault = new int[ref];
        for (int i=0; i <ref; i++) {
            int search = -1;
            for (int k=0; k < frames; k++) {
                if(buffer[k] == pages[i]) {
                    search = k;
                    hit[i] = true;
                    fault[i] = page_faults;
                    break;
                }
            }
            if(search == -1) {
                if(isFull) {
                    int index[] = new int[frames];
                    boolean index2 = new boolean[frames];
                    for(int k= i +1; k<ref; k++) {
                        int max = index[0];
                        pointer = 0;
                        if(max == 0) {
                            max = 200;
                        }
                        for(int j=0; j<frames; j++) {
                            if(index[j] == 0) {
                                index[j] = 200;
                            }
                            if(index[j] > max) {
                                max = index[j];
                                pointer = j;
                            }
                        }
                    }
                    buffer[pointer] = pages[i];
                    page_faults++;
                    fault[i] = page_faults;
                    if(!isFull) {
                        pointer++;
                        if(pointer == frames) {
                            pointer = 0;
                            isFull = true;
                        }
                    }
                }
            }
        }
        this.pageFaultCount = page_faults;
        
    }

    public void useLRU(String referenceString) {
        HashSet<Integer> s = new HashSet<>(cap); 
        HashMap<Integer, Integer> index = new HashMap<>(); 
   
        for (int i=0; i< referenceString.length(); i++) 
        { 
            if (s.size() < cap) 
            { 
                if (!s.contains(pages[i])) 
                { 
                    s.add(pages[i]); 
                    this.pageFaultCount++; 
                }        
                index.put(pages[i], i); 
            }        
            { 
                if (!s.contains(pages[i])) 
                { 
                    int lru = Integer.MAX_VALUE, val=Integer.MIN_VALUE; 
                    Iterator<Integer> next_itr = s.iterator(); 
                    while (next_itr.hasNext()) { 
                        int temp = next_itr.next(); 
                        if (index.get(temp) < lru) 
                        { 
                            lru = index.get(temp); 
                            val = temp; 
                        } 
                    }                   
                    s.remove(val); 
                    s.add(pages[i]);        
                    this.pageFaultCount++; 
                } 
       
                // Update the current page index 
                index.put(pages[i], i); 
            } 
        } 
    }

}
