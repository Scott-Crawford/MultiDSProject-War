import java.util.Random;
@SuppressWarnings("unchecked")
public class MultiDS<T> implements PrimitiveQ<T>, Reorder{
    
    
    private T[] bob; //bob is our main array. He stores all our data.
    private T[] temp;//a temporary array used various times for different reasons
    private int items;//keeps a record of number of items stored

    /**creates a MultiDS object of the desired capacity and sets the number of items in it to zero
     * @param a The integer desired capacity
     */
    public MultiDS(int a){
        
        bob=(T[])new Object[a];
        items=0;
    }
    
    /**Attempts to add a new item into the next avaliable location in bob. If it can be added,
     * it returns a true. If bob is full, it returns a false.
     * @param item The items that is to be added
     * @return true if successfully added, false if the arrary is full and fails to add the item
     */
    public boolean addItem(T item){
        
        int i=0;
        while(i<bob.length){
            if(bob[i]==null){
                bob[i]=item;
                break;
            }
            else if((bob[i]!=null)&&(i==bob.length-1)){
                return false;
                    
            }
            else{
                i++;
            } 
        }
        items++;
        return true;
        
    }
    
    /**Removes the first, or "oldest", item that bob is holding, and shifts the rest of the array
     * up one
     * @return first object of array, or null if the array if empty 
     */
    public T removeItem(){
        T scott=null;
        for(int i=0;i<bob.length;i++){
            if(bob[i]==null){
                continue;
            }
            else{
                
                scott=bob[i];
                bob[i]=null;
                
                if(items==0){
                    
                    break;
                }
                else{
                    
                    temp=(T[])new Object[items];
                    int k=bob.length;
                    for(int j=0;j<items;j++){
                            if(j==items-1){
                                temp[j]=bob[0];
                            }
                            else{
                                temp[j]=bob[j+1];
                            }
                    }
                    bob=(T[])new Object[k];
                    for(int j=0;j<temp.length;j++){
                        bob[j]=temp[j];
                    }
                }
                items--;
                break;
            }
        }
                
        return scott;
    }
    
    /**checks if bob is full by comparing the number of items to the length of bob
     * @return true if the array is full, false if it is not
     */
    public boolean full(){
        if(items==bob.length){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**checks if bob is empty by checking if the number of items is equal to zero
     * return true if empty, false if it is not
     */
    public boolean empty(){
        if(items==0){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**checks the number of items in bob that are not equal to null
     * @return integer of items in bob
     */
    public int size(){
        int x=0;
        for(int i=0;i<bob.length;i++){
            if(bob[i]!=null){
                x++;
            }
            else{
                continue;
            }
        }
        return x;
    }
    
    /**clears bob by reintializing him as a new array with the same length and setting the
     * number of items to zero
     * 
     */
    public void clear(){
        int x=bob.length;
        bob=(T[])new Object[x];
        items=0;
    }
    
    /**reverses the order of the objects that bob is holding, placing the newest item first and
     * the oldest item last
     * 
     */
    public void reverse(){
        temp=(T[])new Object[items];
        int k=bob.length;
        for(int i=0;i<items;i++){
            temp[i]=bob[(items-1)-i];
        }
        bob=(T[])new Object[k];
        for(int i=0;i<temp.length;i++){
            bob[i]=temp[i];
        }
    }
    
    /**shifts the objects bob is holding one to the right, placing the newest item at the front
     * 
     */
    public void shiftRight(){
        temp=(T[])new Object[items];
        int k=bob.length;
        for(int i=0;i<items;i++){
            if(i==0){
                temp[i]=bob[items-1];
            }
            else{
                temp[i]=bob[i-1];
            }
        }
        bob=(T[])new Object[k];
        for(int i=0;i<temp.length;i++){
            bob[i]=temp[i];
        }
    }
    
    /**shifts the objects bob is holding one to the left, placing the oldest item at the end
     * 
     */
    public void shiftLeft(){
        temp=(T[])new Object[items];
        int k=bob.length;
        for(int i=0;i<items;i++){
            if(i==items-1){
                temp[i]=bob[0];
            }
            else{
                temp[i]=bob[i+1];
            }
        }
        bob=(T[])new Object[k];
        for(int i=0;i<temp.length;i++){
            bob[i]=temp[i];
        }
    }
    
    /**randomizes the order of the objects that bob is holding by shifting each spot in bob with
     * with another randomized spot one at a time
     * 
     */
    public void shuffle(){
        Random generator=new Random();
        for(int i=0;i<items;i++){
            int x=generator.nextInt(items);
            T temp=bob[i];
            bob[i]=bob[x];
            bob[x]=temp;
        }
        
    }
    
    /**prints out the objects of bob, without any of the nulls 
     * @return String of the contents of bob
     */
    public String toString(){
        String x="Contents: \n";

        for(int i=0;i<bob.length;i++){
            
            if(bob[i]!=null){
                x=x+bob[i]+" ";
            }
            else{
               continue;
            }
        }
        return x;
    }
    
}