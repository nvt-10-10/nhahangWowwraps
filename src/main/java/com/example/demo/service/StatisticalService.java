package com.example.demo.service;

import com.example.demo.model.data.Statistical;
import com.example.demo.reponsitory.BookRepomsitory;
import com.example.demo.reponsitory.OrderReponsitory;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticalService {
    private final OrderReponsitory orderReponsitory;
    private final BookRepomsitory bookRepomsitory;

    public StatisticalService(OrderReponsitory orderReponsitory, BookRepomsitory bookRepomsitory) {
        this.orderReponsitory = orderReponsitory;
        this.bookRepomsitory = bookRepomsitory;
    }

    public String getValue(List<Object[]> data, int j) {
        for (int i = 0; i < data.size(); i++) {
            if ((int) data.get(i)[0] == j) {
                return data.get(i)[1].toString();
            }
        }
        return "0";
    }


    public List<Statistical> getTotalRevenueByMonth() {
        List<Object[]> data = orderReponsitory.getTotalRevenueByMonth();

        List<Statistical> statistics = new ArrayList<>();
        int i = 1;
        while (i <= 12) {
            statistics.add(new Statistical(Month.of(i).toString(), getValue(data, i)));
            i++;
        }

        return statistics;
    }

    public static String getStatus(int statatus) {
        if (statatus == 0) {
            return "In Progress";
        } else if (statatus == 1) {
            return "Completed";
        } else
            return "Canceled";
    }

    public List<Statistical> getOrderByYear() {
        List<Object[]> data = orderReponsitory.getOrderByYear();
        List<Statistical> statistics = new ArrayList<>();
        for (Object[] item : data
        ) {
            String name = getStatus((int)item[0]);
            String value = item[1].toString();
            statistics.add(new Statistical(name,value));
        }
        return statistics;
    }

    public List<Statistical> getTotalBookByMonth() {
        List<Object[]> data = bookRepomsitory.getTotalBookByMonth();

        List<Statistical> statistics = new ArrayList<>();
        int i = 1;
        while (i <= 12) {
            statistics.add(new Statistical(Month.of(i).toString(), getValue(data, i)));
            i++;
        }

        return statistics;
    }

    public List<Statistical> getBookByYear() {
        List<Object[]> data = bookRepomsitory.getBookByYear();
        List<Statistical> statistics = new ArrayList<>();
        for (Object[] item : data
        ) {
            String name = getStatus((int)item[0]);
            String value = item[1].toString();
            statistics.add(new Statistical(name,value));
        }
        return statistics;
    }
}
