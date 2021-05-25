import java.util.*;
import java.io.*;

class Main {

    public static void main(String[] args) {
        //en la mesa se insertan los datos
        Mesa mesa = new Mesa();
        Agente Monitor = new Agente(mesa);//el Agentee "monitor" controla los hilos
        Monitor.start();


         System.out.println("Problema Fumadores resuelto con semaforos ");
         System.out.println("");

          
        for (int i = 0; i < 3; i++)
        {
            Fumadores FumadoresThread = new Fumadores(mesa, i, "fumador  " + (i+1), Monitor);
            FumadoresThread.start();
        }
    }
}






//-------------------------------------------------------------------------Clase Fumadores-----------------------------------------------
class Fumadores extends Thread {

Mesa mesa = new Mesa();
String item;
int numeroItem;
Agente Monitor;
String name;

    public Fumadores(Mesa nuevaMesa, int numeroItem, String name, Agente monitor)
    {
        this.numeroItem = numeroItem;
        this.mesa = nuevaMesa;
        this.name= name;
        Monitor = monitor;
    }

 
    public void run()
    { 
        while(true)
        {
            item = mesa.itemsFumadores(numeroItem);
            if (!mesa.hasItem(item) && !mesa.isEmpty())
            {

               System.out.println("");
               System.out.println("Como me falta " + item + " , entonces el que tiene que pasa " + name );
              
               if(item=="Encendedor"){
                  System.out.println("___    A");
                  System.out.println("| |   {*}");
                  System.out.println("| |  __V__");
                  System.out.println("|_|o_|%%%|0_");
                  System.out.println("   |       |");
                  System.out.println("   |       |");
                  System.out.println("   |_______|");
                  System.out.println("");
               }
              	System.out.println("-----------------------------------------------------------------------");
                try {
    
                    Accion();
                    System.out.println(name+ " Da permiso que alguien mas fume");
                    Monitor.wake();
                } catch (Exception e) {}
            }
        }
    } 

    public synchronized void Accion() throws Exception
    {
        System.out.println(name + " empieza a fumar");
                  System.out.println("");
                  System.out.println("");
                  System.out.println("a,  8a");
                  System.out.println(" `8, `8)                            ,adPPRg,");
                  System.out.println("  8)  ]8                        ,ad888888888b");
                  System.out.println(" ,8' ,8'                    ,gPPR888888888888");
                  System.out.println(",8' ,8'                 ,ad8''   `Y888888888P");
                  System.out.println("8)  8)              ,ad8''        (8888888''");
                  System.out.println("8,  8,          ,ad8''            d888''");
                  System.out.println("`8, `8,     ,ad8''            ,ad8''");
                  System.out.println(" `8, `' ,ad8''            ,ad8''");
                  System.out.println("    ,gPPR8b           ,ad8''");
                  System.out.println("   dP:::::Yb      ,ad8''");
                  System.out.println("   8):::::(8  ,ad8''");
                  System.out.println("   Yb:;;;:d888''  ");
                  System.out.println("    '8ggg8P'      ");
                  System.out.println("");
                  System.out.println("");
        Thread.sleep(3000);
    }
  }





//--------------------------------------------------------------Clase Agentee-----------------------------------------------------------------
class Agente extends Thread {

   Mesa mesa;

    public Agente(Mesa nuevaMesa)
    {
        mesa = nuevaMesa;
    }

 
    public void run() 
    {
        while(true)
        {
            try {
                Thread.sleep(500);
            } catch (Exception e) {}
            mesa.getRandom();
           
            System.out.println("_______________________________________________________________________");
            System.out.println("_______________________________________________________________________");
            System.out.println("\n Agente: Solo tengo " + mesa.getMonitorItems());
            pause();
        }
    }

    public synchronized void wake()
    {
        try
        {
          notify();
        } catch(Exception e){}
    }


    public synchronized void pause()
    {
        try
        {
          this.wait();
        } catch (Exception e) {}
    }

    
}




//-------------------------------------------------------------------CLASE MESA--------------------------------------------------------------------
class Mesa {

   
 ArrayList allItems  = new ArrayList();
 ArrayList MonitorItems = new ArrayList();

    public Mesa()
    {
        allItems .add("Tabaco");
        allItems .add("Papel");
        allItems .add("Encendedor");
    }
    

    public void getRandom()
    {
        Random random = new Random();
        MonitorItems.clear();

        ArrayList copyAllElements = (ArrayList) allItems .clone();
        int item1 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(item1));

        copyAllElements.remove(item1);
	      
	int item2 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(item2));
    }
    
 
     public boolean isEmpty()
    {
        return (MonitorItems.size() == 0);
    }
    
    
    
    public synchronized String getMonitorItems()
    {
        notifyAll();
        return MonitorItems.toString();
    }
    
   
    public synchronized String itemsFumadores(int x)
    {
        try {
            this.wait();
        } catch (Exception e) {}
        return (String) allItems .get(x);
    }
   
   
    public boolean hasItem(String itemName)
    {
        return (MonitorItems.contains(itemName));
    }



    public synchronized void pause()
    {
        try {
            this.wait();
        } catch (Exception e) {}
    }
}