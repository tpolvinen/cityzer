package com.company;

import ucar.netcdf.NetcdfFile;
import ucar.netcdf.Netcdf;
import ucar.netcdf.Variable;
import ucar.netcdf.VariableIterator;import java.io.IOException;
import java.lang.reflect.Executable;

import ucar.multiarray.*;/**
 * Simple example to print contents of an existing netCDF file of
 * unknown structure, much like ncdump.  A difference is the nesting of
 * multidimensional array data is represented by nested brackets, so the
 * output is not legal CDL that can be used as input for ncgen.
 *
 * @author: Russ Rew
 * @version: $Id: DumpNetcdf.java,v 1.9 1998/07/17 15:24:32 russ Exp $ */
public class Main {
    static String fileName = "C:\\Users\\a1500908\\Documents\\data.bin";
    /**
 * Prints schema (structure) of an existing netCDF file with a
 * specified file name.
 *
 * @param args name of netCDF file to be read.  */
public static void main(String[] args) {
    Communicator theGreatCommunicator = new Communicator();
    try {
        String token = theGreatCommunicator.getToken("haagahelia1", "Dh3AkCr6");
        System.out.print("token:"+token+"|||END");
        if(token==null){
           token="NO-TOKEN";
        }
        theGreatCommunicator.getData(token);
    }catch (IOException x){

    }


    if (true) {
        fileName = "C:\\Users\\a1500908\\Documents\\data.bin";
    }else{
        System.err.println("no netCDF file name specified, exiting ...");
        System.exit(-1);
    }    try {
        Netcdf nc = new NetcdfFile(fileName, true); // open it readonly
        System.out.println(nc); // output schema in CDL form (like ncdump)
        System.out.println("data:");
        VariableIterator vi = nc.iterator();
        while(vi.hasNext()) {
            Variable var = vi.next();
            MultiArray varMa =
                    var.copyout(new int[var.getRank()],
                            var.getLengths());
            System.out.print(var.getName() + " =");
            System.out.println(MultiArrayToString(var));
        }    } catch (java.io.IOException e) {
        e.printStackTrace();
    }    }    public static String
MultiArrayToString(MultiArray ma) {
    StringBuffer buf = new StringBuffer();
    try {
        buf.append(MultiArrayToStringHelper(ma, new IndentLevel()));
    } catch (java.io.IOException e) {
        e.printStackTrace();
    }
    return buf.toString();
}    /**
 * Maintains indentation level for printing nested structures.
 */
static class IndentLevel {
    private int level = 0;
    private int indentation;
    private StringBuffer indent;
    private StringBuffer blanks;    public IndentLevel() {
        this(4);
    }

    public IndentLevel(int indentation) {
        if (indentation > 0)
            this.indentation = indentation;
        indent = new StringBuffer();
        blanks = new StringBuffer();
        for (int i=0; i < indentation; i++)
            blanks.append(" ");
    }    public void incr() {
        level += indentation;
        indent.append(blanks);
    }    public void decr() {
        level -= indentation;
        indent.setLength(level);
    }

    public String getIndent() {
        return indent.toString();
    }
}    private static String
MultiArrayToStringHelper(MultiArray ma, IndentLevel ilev)
        throws java.io.IOException{ // no I/O here, so this won't really happen

    final int rank = ma.getRank();
    if (rank == 0) {
        try {
            return ma.get((int[])null).toString();
        } catch (IOException ee) {
        }
    }
    StringBuffer buf = new StringBuffer();
    buf.append("\n" + ilev.getIndent() + "{");
    ilev.incr();
    final int [] dims = ma.getLengths();
    final int last = dims[0];
    for(int ii = 0; ii < last; ii++)
    {
        final MultiArray inner =
                new MultiArrayProxy(ma, new SliceMap(0, ii));
        buf.append(MultiArrayToStringHelper(inner, ilev));
        if(ii != last - 1)
            buf.append(", ");
    }
    ilev.decr();
    if (rank > 1) {
        buf.append("\n" + ilev.getIndent());
    }
    buf.append("}");

    return buf.toString();
}
}



