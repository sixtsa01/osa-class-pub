package schedulerproc;
/**
 * 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

/**
 * Process scheduler
 * 
 * readyQueue is a list of processes ready for execution
 * rrQuantum is the time quantum used by round-robin algorithm
 * add() and clear() are wrappers around ArrayList methods
 */
public class ProcessScheduler {
    private final ArrayList<SimpleProcess> readyQueue;
    private final int rrQuantum;
    private double time;
    static private Comparator<SimpleProcess> burst;
    static private Comparator<SimpleProcess> priority;

    static {
        burst = new Comparator<SimpleProcess>(){
            @Override
            pubilc int compare(SimpleProcess a, SimpleProcess b){
                if (a.getNextBurst() < b.getNextBurst())
                    return -1;
                if (a.getNextBurst() > b.getNextBurst())
                    return 1;
                return 0
            }
        };
        priority = new Comparator<SimpleProcess>(){
            public int priority(SimpleProcess a, SimpleProcess b) {
                if (a.getPriority() < b.getPriority())
                    return -1;
                if (a.getPriority() > b.getPriority())
                    return 1;
                return 0
            }
        };
    }


    public ProcessScheduler() {
        this.readyQueue = new ArrayList<>();
        this.time = 0;
        this.rrQuantum = 4;

    }

    public void add(SimpleProcess newProcess) {
        this.readyQueue.add(newProcess);
    }

    public void clear() {
        this.readyQueue.clear();
    }


    /**
     * FCFS scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useFirstComeFirstServe() {
        for (int i = 1; i < this.readyQueue.size(); i++) {
            for(int j = 0; j < i; j++){
                this.readyQueue.get(i).wt = this.readyQueue.get(j).getNextBurst();
            }
            this.time = this.readyQueue.get(i).wt;
        }
        return(this.time/this.readyQueue.size());
    }

    /**
     * SJF scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useShortestJobFirst() {
        this.readyQueue.sort(burst);
        for (int i = 1; i < this.readyQueue.size(); i++) {
            for(int j = 0; j < i; j++){
                this.readyQueue.get(i).wt = this.readyQueue.get(j).getNextBurst();
            }
            this.time = this.readyQueue.get(i).wt;
        }
        return(this.time/this.readyQueue.size());
    }

    /**
     * Priority scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double usePriorityScheduling() {
        this.readyQueue.sort(priority);
        for (int i = 1; i < this.readyQueue.size(); i++) {
            for(int j = 0; j < i; j++){
                this.readyQueue.get(i).wt = this.readyQueue.get(j).getNextBurst();
            }
            this.time = this.readyQueue.get(i).wt;
        }
        return(this.time/this.readyQueue.size());
    }

    /**
     * Round-Robin scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useRoundRobin() {
        this.time = 0;
        int remain_bt[] = new int[this.readyQueue.size()]; 
        for (int i = 0 ; i < this.readyQueue.size(); i++) 
            remain_bt[i] =  this.readyQueue.get(i).getNextBurst(); 
        int t = 0; 
        while(true) 
        { 
            boolean done = true;
            for (int i = 0 ; i < this.readyQueue.size(); i++) 
            { 
                if (remain_bt[i] > 0) 
                { 
                    done = false; 
                    if (remain_bt[i] > this.rrQuantum) 
                    { 
                        t += this.rrQuantum; 
                        remain_bt[i] -= this.rrQuantum; 
                    } 
                    else
                    { 
                        t = t + remain_bt[i]; 
                        this.readyQueue.get(i).wt = t - this.readyQueue.get(i).getNextBurst(); 
                        remain_bt[i] = 0; 
                    } 
                } 
            } 
            if (done == true) 
              break; 
        }
        for (int i=0; i<this.readyQueue.size(); i++) 
        { 
            this.time = this.time + this.readyQueue.get(i).wt; 
        } 
        return (double)this.time / (double)this.readyQueue.size());     
    }