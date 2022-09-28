package Util;

import com.perfios.banking.dto.GetTansactionDTO;

import java.util.Comparator;

public class SortByDate implements Comparator<GetTansactionDTO> {
    @Override
    public int compare(GetTansactionDTO o1, GetTansactionDTO o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}