package schedulerdisk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

/**
 *
 * @author Sam
 */
public class DiskScheduler {

    private final int cylinders;
    private int currentCylinder;
    private final int previousCylinder;
    private int totalMoves;

    public DiskScheduler(int cylinders, int currentCylinder, int previousCylinder) {
        this.cylinders = cylinders;
        this.currentCylinder = currentCylinder;
        this.previousCylinder = previousCylinder;
        this.totalMoves = 0;
    }

    public int getTotalMoves() {
        return this.totalMoves;
    }

    public static void calculateDifference(String queue[], int head, SimpleRequest diff[]) {
        for (int i = 0; i < diff.length; i++)
            diff[i].distance = Math.abs(Integer.parseInt(queue[i]) - head);
    }

    public static int findMin(SimpleRequest diff[]) {
        int index = -1, minimum = Integer.MAX_VALUE;
        for (int i = 0; i < diff.length; i++) {
            if (!diff[i].accessed && minimum > diff[i].distance) {
                minimum = diff[i].distance;
                index = i;
            }
        }
        return index;
    }

    public void useFCFS(String requestQueue) {
        String fcfs1 = Integer.toString(this.currentCylinder);
        requestQueue = fcfs1 + "," + requestQueue;
        String[] fcfs_split = requestQueue.split(",");
        int[] queue = new int[fcfs_split.length];
        for (int i =0; i < fcfs_split.length;i++){
            queue[i] =Integer.parseInt(fcfs_split[i]);
        }
        int totalFcfs = 0;
        int diff_1 = 0;
        int diff_2 = 0;
        int value = 0;
        for(int i=0;i<queue.length;i++) {
            if (i == (queue.length -1)) {
                diff_2 = queue[i];
            }
            else{
                diff_2 = queue[i+1];
            }
            diff_1 = queue[i];
            if (diff_2 > diff_1) {
                value = diff_2 - diff_1;
                totalFcfs += value;
            }
            else{
                value = diff_1 - diff_2;
                totalFcfs += value;
            }
            this.totalMoves = totalFcfs;
        }

    }

    public void useSSTF(String requestQueue){
        String[] sstf_split = requestQueue.split(",");
        if (sstf_split.length == 0)
            return;

        SimpleRequest diff[] = new SimpleRequest[sstf_split.length];
        for (int i = 0; i < diff.length; i++)

            diff[i] = new SimpleRequest(Integer.parseInt(sstf_split[i]));

        for (int i = 0; i < sstf_split.length; i++) {

            calculateDifference(sstf_split, this.currentCylinder, diff);

            int index = findMin(diff);

            diff[index].accessed = true;

            this.totalMoves += diff[index].distance;

            this.currentCylinder = Integer.parseInt(sstf_split[index]);
        }


}

    public void useLOOK(String requestQueue) {
        String look1 = Integer.toString(this.currentCylinder);
        requestQueue = look1 + "," + requestQueue;
        String[] look_split = requestQueue.split(",");
        int[] look_split1 = new int[look_split.length];
        for (int i = 0; i < look_split.length; i++) {
            look_split1[i] = Integer.parseInt(look_split[i]);
        }
        Arrays.sort(look_split1);
        int index = Arrays.binarySearch(look_split1, this.currentCylinder);
        int[] previous = new int[look_split1.length];
        int[] starter = new int[look_split1.length + 1];
        for (int i = 0; i < look_split1.length; i++) {
            if (previous[index] < starter[index]) {
                if (this.currentCylinder < index) {
                    previous[i] = this.previousCylinder;
                } else {
                    starter[i] = this.currentCylinder;
                }
            }
        for (int j = 0; j < look_split1.length; j--) {
            if (this.currentCylinder < index) {
                previous[j] = this.previousCylinder;
            } else {
                starter[i] = this.currentCylinder;
            }
        }
        }
    }

    public void useCLOOK(String requestQueue) {
        String clook1 = Integer.toString(this.currentCylinder);
        requestQueue = clook1 + "," + requestQueue;
        String[] clook_split = requestQueue.split(",");
        int[] clook_split1 = new int[clook_split.length];
        for (int i = 0; i < clook_split.length; i++) {
            clook_split1[i] = Integer.parseInt(clook_split[i]);
        }
        Arrays.sort(clook_split1);
        int index = Arrays.binarySearch(clook_split1, this.currentCylinder);
        int[] previous = new int[clook_split1.length];
        int[] starter = new int[clook_split1.length + 1];
        for (int i = 0; i < clook_split1.length; i--) {
            if (this.currentCylinder < index) {
                previous[i] = this.previousCylinder;
            } else {
                starter[i] = this.currentCylinder;
            }
        }
        for (int j = 0; j < clook_split1.length; j++) {
            if (this.currentCylinder < index) {
                previous[j] = this.previousCylinder;
            } else {
                starter[j] = this.currentCylinder;
            }
            }
        }
    }

