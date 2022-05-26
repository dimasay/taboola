package second;

import java.time.LocalDateTime;
import java.util.*;

public class MyClass {
    //LocalDateTime is thread safe. Date no
    private final LocalDateTime time;
    //    private final Date time;
    private final String name;
    //Is it needed to use long? I think INT type may be better choice for regular cases. But I can not change it without context.
    //We don't use any benefit of Long type here. I think it's better to use long.
    private final long[] numbers;
    //    private final List<Long> numbers;
    private final List<String> strings;

    public MyClass(LocalDateTime time, String name, long[] numbers, List<String> strings) {
        this.time = time;
        this.name = name;
        //Class doesn't have any "add value to array" methods. It means, I can do sort on this step.
        Arrays.sort(numbers);
        this.numbers = numbers;
        this.strings = strings;
    }

//    public boolean equals(Object obj) {
//        if (obj instanceof MyClass) {
//            return name.equals(((MyClass) obj).name);
//        }
//        return false;
//    }
//

    //I use case when field name enough for correct comparison
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyClass myClass = (MyClass) o;
        return name.equals(myClass.name);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);

        //old code
//        String out = name;
        for (long item : numbers)
            sb.append(" ").append(item);
        //old code
//        return out;
        return sb.toString();
    }

    public void removeString(String str) {
        //old code
//        for (int i = 0; i < strings.size(); i++) {
//            if (strings.get(i).equals(str)) {
//                strings.remove(i);
//            }
//        }
        //new code
        strings.remove(str);
    }

    public boolean containsNumber(long number) {
        return Arrays.binarySearch(numbers, number) > -1;
//        for (long num : numbers) {
//            if (num == number) {
//                return true;
//            }
//        }
//        return false;
    }

    public boolean isHistoric() {
        return time.isBefore(LocalDateTime.now());
    }
}