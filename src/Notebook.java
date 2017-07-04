public class Notebook implements Comparable<Notebook>{
    private String Id, Creator;
    private double Ram,Frequency;
    public Notebook (String id,String creator,  double frequency, double ram){
        this.setId(id);
        this.setRam(ram);
        this.setCreator(creator);
        this.setFrequency(frequency);
    }
    public String getId () {return Id;}
    public double getRam () {return  Ram;}
    public String getCreator () {return Creator;}
    public double getFrequency () {return Frequency;}
    public void setId (String id) {this.Id = id;}
    public void setRam (double ram) {this.Ram = ram;}
    public void setCreator (String creator) {this.Creator = creator;}
    public void setFrequency (double frequency) {this.Frequency = frequency;}
    @Override
    public int compareTo(Notebook n) {
        if (this.Ram == n.Ram){
            return 0;
        }
        else if (this.Ram >= n.Ram){
            return 1;
        }
        return -1;
    }
    @Override
    public String toString(){
        return Id+" "+Creator+" "+Frequency+" "+Ram;
    }
}