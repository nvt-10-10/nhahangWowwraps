package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Student implements Comparable<Student>{
    private int id;
    private String name;
    private String email;
    private double toan;
    private double ly;
    private double hoa;
    @Override
    public int compareTo(Student otherStudent) {
        double thisDTB = this.DTB();
        double otherDTB = otherStudent.DTB();

        if (thisDTB < otherDTB) {
            return -1;
        } else if (thisDTB > otherDTB) {
            return 1;
        } else {
            return 0;
        }
    }


    public double DTB(){
        return (toan+ly+hoa)/3;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", toan=" + toan +
                ", ly=" + ly +
                ", hoa=" + hoa +
                ",DTB="+ DTB()+
                '}';
    }
}
