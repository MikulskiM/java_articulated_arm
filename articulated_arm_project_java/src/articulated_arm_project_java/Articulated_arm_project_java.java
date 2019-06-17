/*
 * 
 * "THE BEER-WARE LICENSE" (Revision 42):
 * Marek Mikulski and Maciej Kuraż wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy as a beer in return
 *
 */
package articulated_arm_project_java;


import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;





/**
 *
 * @author Praca
 */


public class Articulated_arm_project_java extends JFrame implements KeyListener {

    BranchGroup     wezel_scena, scena;
    BoundingSphere  bounds;
    Transform3D pos_podloga, pos_podstawa, pos_korpus, pos_przegub_1, pos_ramie, pos_przegub_2, pos_chwytak,
                obrot_korpus_p, obrot_korpus_l, obrot_ramie_d, obrot_ramie_g, obrot_chwytak_d, obrot_chwytak_g;
    TransformGroup  robot, podloga_TG, podstawa_TG, korpus_TG, przegub_1_TG, ramie_TG, przegub_2_TG, chwytak_TG;
    
    
    Vector3f polozenie_robot        = new Vector3f(0.0f,0.0f,0.0f);
    Vector3f polozenie_podloga      = new Vector3f(0.0f,-0.7f,0.0f);
    Vector3f polozenie_podstawa     = new Vector3f(0.0f,-0.6f,0.0f);
    Vector3f polozenie_korpus       = new Vector3f(0.0f,0.0f,0.0f);
    Vector3f polozenie_przegub_1    = new Vector3f(0.0f,0.58f,0.0f);
    Vector3f polozenie_ramie        = new Vector3f(-0.38f,0.0f,-0.4f);
    Vector3f polozenie_przegub_2    = new Vector3f(0.0f,0.5f,0.0f);
    Vector3f polozenie_chwytak      = new Vector3f(-0.28f,0.0f,-0.08f);
    

    Transform3D tmp_rot_Z_45        = new Transform3D();
    Transform3D tmp_rot_Z_90        = new Transform3D();
    
    Transform3D tmp_rot_X_90        = new Transform3D();
    Transform3D tmp_rot_X_270       = new Transform3D(); 

    Transform3D tmp_rot_X_345        = new Transform3D();
    
    
    Articulated_arm_project_java(){
        super("JAVA PROJEKCIK - MIKULSKI, KURAŻ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1200,900));
        canvas3D.addKeyListener(this);
        
        add(canvas3D);
        pack();
        setVisible(true);
        BranchGroup scena = create_scene();
	scena.compile();

        this.addKeyListener(this);
        
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        
        Transform3D przesuniecie_obserwatora = new Transform3D();                       // ustawienie gdzie jest obserwator
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,6.0f));
        universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        
                
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE); 
        orbit.setSchedulingBounds(new BoundingSphere()); // umożlwienie obracania kamerą
        orbit.setBounds(new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 20.0d));

        universe.getViewingPlatform().setViewPlatformBehavior(orbit);
        universe.addBranchGraph(scena);
        
    }
    
    
    BranchGroup create_scene(){
        
      wezel_scena = new BranchGroup();
      bounds = new BoundingSphere(new Point3d(0, 0, 0), 5);
      
      
      swiatlo();
      robot();
      
      return wezel_scena;
    }
     
    public void swiatlo() {
    
      Color3f kolor_swiatla_kier     = new Color3f(0.7f, 0.7f, 0.75f);
      BoundingSphere obszar_ogr =  new BoundingSphere(new Point3d(0.0d,0.0d,0.0d), 10.0d);
      Vector3f kierunek_swiatla_kier = new Vector3f(4.0f, -5.0f, -15.0f);
      
      DirectionalLight swiatlo_kier = new DirectionalLight(kolor_swiatla_kier, kierunek_swiatla_kier);
      swiatlo_kier.setInfluencingBounds(obszar_ogr);
      
      wezel_scena.addChild(swiatlo_kier);
           
    }
        
    
     public void robot() {  
    

      tmp_rot_Z_45.rotZ(Math.PI/4);
      tmp_rot_Z_90.rotZ(Math.PI/2);
      
      tmp_rot_X_90.rotX(Math.PI/2);
      tmp_rot_X_270.rotX(-Math.PI/2);
      
      tmp_rot_X_345.rotX(-Math.PI/12);
         
      Transform3D tmp_rot = new Transform3D();
      
      
      Material material_ramion = new Material(new Color3f(17.0f, 0.292f,0.0f), new Color3f(17.2f, 0.2f, 0.2f), 
                                             new Color3f(17.0f, 1.0f,0.0f), new Color3f(17.0f, 0.0f,1.0f), 128.0f);
      
      Material material_przegubow = new Material(new Color3f(0.3f, 0.3f,0.3f), new Color3f(0.3f,0.3f,0.3f),
                                                new Color3f(0.3f, 0.3f, 0.3f), new Color3f(0.3f, 0.3f, 0.3f), 20.0f);
      
      ColoringAttributes cattr = new ColoringAttributes();
      cattr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
      
      Appearance wyglad_ramion = new Appearance();
      Appearance wyglad_przegubow = new Appearance();
      
      wyglad_ramion.setMaterial(material_ramion);
      wyglad_ramion.setColoringAttributes(cattr);
      wyglad_przegubow.setMaterial(material_przegubow);
      wyglad_przegubow.setColoringAttributes(cattr);
      
      //PODLOGA
      
      Appearance  wyglad_podloga = new Appearance();
      wyglad_podloga.setColoringAttributes(new ColoringAttributes(0.2f,0.2f,0.2f,ColoringAttributes.NICEST));
      Box podloga = new Box(8.0f, 0.01f, 8.0f, wyglad_podloga);        
      Transform3D pos_podloga = new Transform3D();
      pos_podloga.set(polozenie_podloga);      
      pos_podloga.mul(tmp_rot);
      podloga_TG = new TransformGroup(pos_podloga);
      podloga_TG.addChild(podloga);
      wezel_scena.addChild(podloga_TG); 
      
      
     // PODSTAWA

      Appearance wygladPodstawa = new Appearance();
      wygladPodstawa.setColoringAttributes(new ColoringAttributes(0.5f,0.6f,0.6f,ColoringAttributes.NICEST));
      Cylinder podstawa = new Cylinder(0.8f, 0.2f, wyglad_przegubow);
      Transform3D pos_podstawa = new Transform3D();
      pos_podstawa.set(polozenie_podstawa);
      pos_podstawa.mul(tmp_rot);
      podstawa_TG = new TransformGroup(pos_podstawa);
      podstawa_TG.addChild(podstawa);
      wezel_scena.addChild(podstawa_TG);

      
      
      // KORPUS
      
      Appearance wygladKorpus = new Appearance();
      wygladKorpus.setColoringAttributes(new ColoringAttributes(2.4f,0.02f,0.02f,ColoringAttributes.NICEST));
      Box korpus = new Box(0.1f, 0.5f, 0.1f, wyglad_ramion);
      
      Transform3D pos_korpus = new Transform3D();
      pos_korpus.set(polozenie_korpus);
      pos_korpus.mul(tmp_rot);
      korpus_TG = new TransformGroup(pos_korpus);
      korpus_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      korpus_TG.addChild(korpus);
     // wezel_scena.addChild(korpus_TG);

     
      //PRZEGUB 1

      Appearance wygladPrzegub = new Appearance();
      wygladPrzegub.setColoringAttributes(new ColoringAttributes(0.6f,0.6f,0.6f,ColoringAttributes.NICEST));
      Cylinder przegub_1 = new Cylinder(0.15f, 0.21f, wyglad_przegubow);
      Transform3D pos_przegub_1 = new Transform3D();
      pos_przegub_1.set(polozenie_przegub_1);
      pos_przegub_1.mul(tmp_rot_X_90);
      przegub_1_TG = new TransformGroup(pos_przegub_1);
      przegub_1_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      przegub_1_TG.addChild(przegub_1);
     // wezel_scena.addChild(przegub_1_TG);
      
      //RAMIE
      
      Appearance wygladRamie = new Appearance();
      wygladRamie.setColoringAttributes(new ColoringAttributes(2.4f,0.02f,0.02f,ColoringAttributes.NICEST));
      Box ramie = new Box(0.1f, 0.5f, 0.1f, wyglad_ramion);
      Transform3D pos_ramie = new Transform3D();
      pos_ramie.set(polozenie_ramie);
      pos_ramie.mul(tmp_rot_X_270);
      pos_ramie.mul(tmp_rot_Z_45);
      ramie_TG = new TransformGroup(pos_ramie);
      ramie_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      ramie_TG.addChild(ramie);
     // wezel_scena.addChild(ramie_TG);
     
      Cylinder przegub_2;
        przegub_2 = new Cylinder(0.15f, 0.21f, wyglad_przegubow);
      Transform3D pos_przegub_2 = new Transform3D();
      pos_przegub_2.set(polozenie_przegub_2);
      pos_przegub_2.mul(tmp_rot_X_90);
      przegub_2_TG = new TransformGroup(pos_przegub_2);
      przegub_2_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      przegub_2_TG.addChild(przegub_2);
     // wezel_scena.addChild(przegub_2_TG);
      
      //CHWYTAK     
     
      Box chwytak = new Box(0.1f, 0.25f, 0.1f, wyglad_ramion);
      Transform3D pos_chwytak = new Transform3D();
      pos_chwytak.set(polozenie_chwytak);
      pos_chwytak.mul(tmp_rot_Z_90);
      pos_chwytak.mul(tmp_rot_X_345);
      chwytak_TG = new TransformGroup(pos_chwytak);
      chwytak_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      chwytak_TG.addChild(chwytak);
     // wezel_scena.addChild(chwytak_TG);
      
    
      //SKLADANIE ROBOTA
      
  
        korpus_TG.addChild(przegub_1_TG);
        przegub_1_TG.addChild(ramie_TG);
        ramie_TG.addChild(przegub_2_TG);
        przegub_2_TG.addChild(chwytak_TG);
      
        wezel_scena.addChild(korpus_TG);
    
        
      //OBRACANIE ROBOTA PRZYCISKAMi
        obrot_korpus_p = new Transform3D();
        obrot_korpus_p.rotY(-Math.PI/50);
        
        obrot_korpus_l = new Transform3D();
        obrot_korpus_l.rotY(Math.PI/50);
        
        obrot_ramie_d = new Transform3D();
        obrot_ramie_d.rotZ(-Math.PI/50);
        
        obrot_ramie_g = new Transform3D();
        obrot_ramie_g.rotZ(Math.PI/50);
        
        obrot_chwytak_d = new Transform3D();
        obrot_chwytak_d.rotZ(-Math.PI/50);
        
        obrot_chwytak_g = new Transform3D();
        obrot_chwytak_g.rotZ(Math.PI/50);
      
      
    }  
      
        
       
    
    
    
    
    
    
    public static void main(String[] args) {
        
        new Articulated_arm_project_java();
        ImageIcon iconic = new ImageIcon("pic.png");
        JOptionPane.showMessageDialog(null, "", "INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE, iconic);
        
        
        
    }

    

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()){
            case KeyEvent.VK_N:
            
                pos_korpus.mul(obrot_korpus_p);
                korpus_TG.setTransform(pos_korpus);  
                break;
                
            case KeyEvent.VK_M:
           
                pos_korpus.mul(obrot_korpus_l);
                korpus_TG.setTransform(pos_korpus);  
                break;
                
            case KeyEvent.VK_J:
           
                pos_przegub_1.mul(obrot_ramie_d);
                przegub_1_TG.setTransform(pos_przegub_1);  
                break;
                
            case KeyEvent.VK_H:
           
                pos_przegub_1.mul(obrot_ramie_g);
                przegub_1_TG.setTransform(pos_przegub_1);
                break;
                
            case KeyEvent.VK_Y:
           
                pos_przegub_2.mul(obrot_chwytak_d);
                przegub_2_TG.setTransform(pos_przegub_2); 
                break;
                
            case KeyEvent.VK_U:
           
                pos_przegub_2.mul(obrot_chwytak_g);
                przegub_2_TG.setTransform(pos_przegub_2); 
                break;
                
                }
        }
    
               
    
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}


    
}
