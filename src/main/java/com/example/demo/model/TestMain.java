package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TestMain {
    static Scanner sc = new Scanner(System.in);
    private static List<Student> students;

    public static boolean checkid(int id) {
        for (Student student : students) {
            if (id == student.getId())
                return false;
        }
        return true;
    }

    public static boolean checkEmail(String email) {
        if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            return true;
        return false;
    }



    public static void xuat() {
        Collections.sort(students);
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }

    public static void nhap() {
        String text;
        int id=0;
        String name=null, email = null;
        double toan=0, ly=0, hoa=0;
        boolean check;
        do {
            check = false;
            System.out.println("Nhap id: ");
            text = sc.nextLine();
            try {
                id = Integer.parseInt(text);
                check = !checkid(id);
            } catch (Exception e) {
                check = true;
            }

        } while (check);

        do {
            System.out.println("Nhap name: ");
            name = sc.nextLine();
        } while (name.length()==0 && name==null);
        do {
            check = false;
            System.out.println("Nhap email: ");
            email = sc.nextLine();

        } while (!checkEmail(email));

        do {
            check = false;
            System.out.println("Nhap toan: ");
            text = sc.nextLine();
            try {
                toan = Double.parseDouble(text);
                if (toan>10||toan<0)
                    check=true;
            } catch (Exception e) {
                check = true;
            }

        } while (check);

        do {
            check = false;
            System.out.println("Nhap Ly: ");
            text = sc.nextLine();
            try {
                ly = Double.parseDouble(text);
                if (ly>10||ly<0)
                    check=true;
            } catch (Exception e) {
                check = true;
            }

        } while (check);
        do {
            check = false;
            System.out.println("Nhap toan: ");
            text = sc.nextLine();
            try {
                hoa = Double.parseDouble(text);
                if (hoa>10||hoa<0)
                    check=true;
            } catch (Exception e) {
                check = true;
            }

        } while (check);



        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        student.setToan(toan);
        student.setLy(ly);
        student.setHoa(hoa);
        students.add(student);
    }

    public static void main(String[] args) {
    students = new ArrayList<Student>();
        String key ="";
//        do {
//            nhap();
//            System.out.println("ban co tt k: ");
//            key = sc.nextLine();
//
//          } while (!key.equals("no"));
//        xuat();
        String str = sc.nextLine();
        String arr[]= str.split("/");
        long sum =0;
        for(String i : arr){
            int x = Integer.parseInt(i);
            do {
                sum+=x%10;
                x/=10;
            } while (x>0);
        }
        long kq=0;
        do {
            kq+=sum%10;
            sum/=10;
        } while (sum>0);
        System.out.println(kq);
    }
}

