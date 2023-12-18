package threads;

import control.BackgroundScreenController;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Cliente extends Thread {
  private ImageView cortando;

  public Cliente(ImageView cortando){
    this.cortando = cortando;
  }

  @Override
  public void run(){
    try{
      BackgroundScreenController.mutex.acquire();
      if(BackgroundScreenController.esperando < BackgroundScreenController.CADEIRAS){
        BackgroundScreenController.esperando++;
        if(BackgroundScreenController.esperando > 0){
          Platform.runLater(()->BackgroundScreenController.filaClientes.get(BackgroundScreenController.esperando - 1).setVisible(true));
        }
        sleep(800);
        BackgroundScreenController.clientes.release();
        BackgroundScreenController.mutex.release();
        BackgroundScreenController.barbeiros.acquire();
        sentarNaCadeira();
      } else{
        BackgroundScreenController.mutex.release();
      }
      vaiEmboraBarbearia();
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  private void sentarNaCadeira(){
    try{
      Platform.runLater(()->BackgroundScreenController.filaClientes.get(0).setVisible(false));
      Platform.runLater(()->cortando.setVisible(true));
      sleep(300);
      if(BackgroundScreenController.esperando < 5 && BackgroundScreenController.esperando >= 0)
      Platform.runLater(()->BackgroundScreenController.filaClientes.get(BackgroundScreenController.esperando).setVisible(false));
      else
      Platform.runLater(()->BackgroundScreenController.filaClientes.get(4).setVisible(false));
      if(BackgroundScreenController.esperando > 0)      
      Platform.runLater(()->BackgroundScreenController.filaClientes.get(0).setVisible(true));

    } catch(Exception e){
      e.printStackTrace();
    }
  }

  private void vaiEmboraBarbearia(){
  }
}
