import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Cartesian
 * Created by jose on 4/14/16.
 * Last edited on 4/14/16
 */
class CartesianProduct
{
    public static void main(String[] args)
    {
        CartesianProduct c = new CartesianProduct();
        try
        {
            if(args.length > 0)
                System.out.println(c.getProduct(args[0]));
        }
        catch (UnclosedBracket e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Starts recursive loop to find the cartesian product
     */
    String getProduct(String input) throws UnclosedBracket
    {
        if(!bracketsBalanced(input))
            throw new UnclosedBracket("Brackets not balanced: " + input);
        String[] inputArray = splitByFirstArray(input);
        String firstInput = inputArray[0];
        String secondInput = "";
        String thirdInput = "";

        if(inputArray.length > 1)
            secondInput = inputArray[1];
        if(inputArray.length > 2)
            thirdInput = inputArray[2];

        ArrayList<String> list = getProduct(firstInput, secondInput, thirdInput, new ArrayList<>());

        return listToString(list);
    }

    /*
     * checks if input has all brackets are closed in input string
     */
    private boolean bracketsBalanced(String input) {
        int i = 0;
        for (char c : input.toCharArray()) {
            if (c == '{')
                i++;
            if (c == '}')
                i--;

            if(i < 0)
                return false;
        }

        return i == 0;
    }

    /*
     * recursive function that takes in the the string split into 3 parts
     * input1 is values that are immediately after the left most array
     * input2 is values inside of the array
     * input3 is values immediately after the array
     * finalproductlist is contains the calculated products
     */
    private ArrayList<String> getProduct(String input1, String input2, String input3, ArrayList<String> finalProductList)
    {
        if(input2.equals("") && input3.equals(""))
        {
            String[] array = input1.split(",");
            Collections.addAll(finalProductList, array);

            return finalProductList;
        }

        ArrayList<String> list = new ArrayList<>();
        if(input2.contains("{"))
        {
            String[] inputArray = splitByFirstArray(input2);
            String[] input1Array = input1.split(",");
            String firstInput = inputArray[0];
            String secondInput = "";
            String thirdInput = "";

            if(inputArray.length > 1)
                secondInput = inputArray[1];
            if(inputArray.length > 2)
                thirdInput = inputArray[2];

            list.addAll(getProduct(firstInput, secondInput, thirdInput, new ArrayList<>()));

            if(input1Array.length == 1)
                for(int i=0; i<list.size(); i++)
                    list.set(i, input1Array[0] + list.get(i));
        }
        else
        {
            String[] input1Array = input1.split(",");
            String[] input2Array = input2.split(",");
            if (input1Array.length == 0)
            {
                for (String entry : input2Array)
                {
                    list.add(input1 + entry);
                }
            }
            else
            {
                int i = 0;
                while (i < input1Array.length - 1)
                {
                    finalProductList.add(input1Array[i]);
                    i++;
                }

                for (String entry : input2Array)
                {
                    list.add(input1Array[input1Array.length - 1] + entry);
                }
            }
        }

        if(!input3.equals(""))
        {
            if(input3.contains("{"))
            {
                String[] inputArray = splitByFirstArray(input3);
                String firstInput = inputArray[0];
                String secondInput = "";
                String thirdInput = "";

                if(inputArray.length > 1)
                    secondInput = inputArray[1];
                if(inputArray.length > 2)
                    thirdInput = inputArray[2];

                ArrayList<String> list2 = getProduct(firstInput, secondInput, thirdInput, new ArrayList<>());

                for(String input3Entry: list2)
                {
                    finalProductList.addAll(list.stream().map(input2Entry -> input2Entry + input3Entry).collect(Collectors.toList()));
                }
            }
            else
            {
                String[] input3Array = input3.split(",");

                finalProductList.addAll(list.stream().map(aList -> aList + input3Array[0]).collect(Collectors.toList()));

                if(input3Array.length > 1)
                {
                    finalProductList.addAll(Arrays.asList(input3Array).subList(1, input3Array.length));
                }
            }
        }
        else
        {
            finalProductList.addAll(list);
        }

        return finalProductList;
    }

    /*
     * splits a given string into the 3 parts based on left most array, whats before the array, whats in the array, and whats after the array
     * ex. a{b,c}d -> a / b,c / d
     */
    private String[] splitByFirstArray(String string)
    {
        if(string.contains("{"))
        {
            int firstSplit = 0, secondSplit = string.length(), bracketsFound = 0;
            boolean firstSplitFound = false;

            for(int i=0; i<string.length(); i++)
            {
                if(string.charAt(i) == '{')
                    if (!firstSplitFound) {
                        firstSplit = i;
                        firstSplitFound = true;
                    } else {
                        bracketsFound++;
                    }

                if(string.charAt(i) == '}')
                {
                    if(bracketsFound ==0)
                    {
                        secondSplit = i;
                        break;
                    }
                    else
                    {
                        bracketsFound--;
                    }
                }
            }

            String firstStringSplit = string.substring(0, firstSplit);
            ArrayList<String> list = new ArrayList<>();
            if(!firstStringSplit.equals(""))
                list.add(firstStringSplit);
            list.add(string.substring(firstSplit + 1, secondSplit));
            if(secondSplit +1 < string.length())
            {
                String lastSplit = string.substring(secondSplit + 1, string.length());
                if(!lastSplit.equals(""))
                    list.add(lastSplit);
            }

            return list.toArray(new String[list.size()]);
        }

        return new String[]{string};
    }

    /*
     * converts array list to string
     */
    private String listToString(ArrayList<String> stringList) {
        String output = "";

        if(stringList.size() > 0)
        {
            for (String listEntry : stringList)
            {
                output += listEntry + " ";
            }
            output = output.substring(0, output.length() - 1);
        }

        return output;
    }
}
