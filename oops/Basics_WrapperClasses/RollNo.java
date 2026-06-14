public class RollNo {
    public static void main(String[] args) {
        //store 5 rollno
        int[] num={1,2,3,4,5};
        //store 5 name
        String[] names=new String[5];
        Student[] students=new Student[5];
        Student sriram = new Student(13,"sriram",85);
        // sriram.rno=13;
        // sriram.name="Sriram";
        // System.out.println(sriram.rno);
        // System.out.println(sriram.name);
        // System.out.println(sriram.marks);
        
        sriram.greeting();
 
    }
    
}
class Student{
        int rno;
        String name;
        float marks;
        void changename(String newname){
            name=newname;
        }
        void greeting(){
            System.out.println("Hello my name is "+name);
        }
        Student(int rno,String name,float marks){
            this.rno=rno;
            this.name="Sriram";
            this.marks=89;
        }
    }
