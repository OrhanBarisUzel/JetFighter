import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.io.IOException;
public class JetFighter extends JPanel implements KeyListener, ActionListener {
    private static final long serialVersionUID = 1L; // Added to eliminate serializable warning
    private Random randomNumber;
    private int combined=0;
    private int GameWidth = 600;
    private int GameHeight = 800;
    private int planeX = 200;
    private int planeY = 500;
    private int Health = 5;
    private int bulletCooldown = 0;
    private JLabel scoreLabel;
    private JLabel healthLabel;
    private Rectangle HealthBar;
    private int score;
    private int BackgroundY,backgroundHeight1;
    private int backgroundY2 = 0,BackgroundHeight2;
    private int backgroundY3=0,BackgroundHeight3;
    private long lastUpdateTime;
    private int frameCount;
    private int fps;
    private static final int MAX_BULLET_COOLDOWN = 20;
    private List<Rectangle> Bullets;
    private List<Rectangle> EnemyBullets;
    private List<EnemyPlane> enemyPlanes;
    private Player CurrentPlayer;
    private DataManager DataManager;
    
    


    public JetFighter() {
    	DataManager= new DataManager();
        setLayout(null);
        setFocusable(true);
        setPreferredSize(new Dimension(GameWidth, GameHeight));
        addKeyListener(this);
        ImageIcon enemyBulletIcon = new ImageIcon("EnemyBullet2.png");
        Bullets = new ArrayList<>();
        enemyPlanes = new ArrayList<>();
        EnemyBullets = new ArrayList<>();

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(10, 10, 100, 20);
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);
        
        healthLabel= new JLabel("Health");
        healthLabel.setBounds(10,30,100,20);
        healthLabel.setForeground(Color.WHITE);
        add(healthLabel);
        HealthBar = new Rectangle(10, 50, 100, 20);
        addKeyListener(this);
        lastUpdateTime = System.nanoTime();

        Timer timer = new Timer(16, this);
        timer.start();

        Timer enemyTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        });
        enemyTimer.start();
        Timer fpsTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fps = frameCount;
                frameCount = 0;
            }
        });
        fpsTimer.start();
    }
    

    private void spawnEnemy() {
        randomNumber = new Random();
        int enemyX = randomNumber.nextInt(GameWidth - 50);
        int enemyY = 0; // Start from the top
        EnemyPlane enemyPlane = new EnemyPlane(enemyX-10, enemyY);
        enemyPlanes.add(enemyPlane);

        // Spawn enemy bullets for this enemy
        spawnEnemyBullet(enemyPlane);
    }

    private void spawnEnemyBullet(EnemyPlane enemyPlane) {
        Rectangle enemyBullet2 = new Rectangle(enemyPlane.getX() + enemyPlane.getWidth() - 10,
                enemyPlane.getY() + enemyPlane.getHeight(), 10, 20);

        EnemyBullets.add(enemyBullet2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundIcon1 = new ImageIcon("Background.jpg");
        backgroundHeight1 = backgroundIcon1.getIconHeight();
        g.drawImage(backgroundIcon1.getImage(), 0, BackgroundY, GameWidth, GameHeight, null);
        

        // Draw the second background
        
        ImageIcon backgroundIcon2 = new ImageIcon("Background.jpg");
        int backgroundHeight2 = backgroundIcon2.getIconHeight();
        g.drawImage(backgroundIcon2.getImage(), 0, backgroundY2-backgroundHeight2, GameWidth, GameHeight, null);
        
        ImageIcon backgroundIcon3 = new ImageIcon("Background.jpg");
        int backgroundHeight3 = backgroundIcon3.getIconHeight()+GameHeight;
        g.drawImage(backgroundIcon3.getImage(), 0, backgroundY3 - backgroundHeight3, GameWidth, GameHeight, null);
		
        // Draw the plane
        ImageIcon icon = new ImageIcon("Plane.png");
        g.drawImage(icon.getImage(), planeX, planeY, icon.getIconWidth() - 50, icon.getIconHeight() - 50, null);
        ImageIcon BulletIcon = new ImageIcon("Bullet.png");
        for (Rectangle bullet : Bullets) {
            g.drawImage(BulletIcon.getImage(), bullet.x, bullet.y, bullet.width + 10, bullet.height + 20, null);
        }
        ImageIcon enemyPlaneIcon = new ImageIcon("EnemyPlane.png");
        ImageIcon enemyBulletIcon = new ImageIcon("EnemyBullet2.png");

        for (EnemyPlane enemyPlane : enemyPlanes) {
            g.drawImage(enemyPlaneIcon.getImage(), enemyPlane.getX(), enemyPlane.getY(), enemyPlaneIcon.getIconWidth(),
                    enemyPlaneIcon.getIconHeight(), null);
        }
        for (Rectangle bullet : EnemyBullets) {
            g.drawImage(enemyBulletIcon.getImage(), bullet.x, bullet.y, bullet.width + 10, bullet.height + 20, null);
        }
        g.setColor(Color.RED);
        g.fillRect(HealthBar.x, HealthBar.y, HealthBar.width, HealthBar.height);
       
    }

    private void gameLogic() {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastUpdateTime) / 1e9;

        // Update bullet positions
        Iterator<Rectangle> iterator = Bullets.iterator();
        while (iterator.hasNext()) {
            Rectangle bullet = iterator.next();
            bullet.y -= 15; // Move bullet upwards

            // Remove bullets that go off the top of the panel
            if (bullet.y + bullet.height < 0) {
                iterator.remove();
            }
        }
        Iterator<EnemyPlane> enemyIterator = enemyPlanes.iterator();
        while (enemyIterator.hasNext()) {
            EnemyPlane enemyPlane = enemyIterator.next();
            enemyPlane.move();

            if (enemyPlane.getY() > GameHeight) {
                enemyIterator.remove();
            }
        }
        Iterator<Rectangle> enemyBulletIterator = EnemyBullets.iterator();
        while (enemyBulletIterator.hasNext()) {
            Rectangle enemyBullet = enemyBulletIterator.next();
            enemyBullet.y += 5;

            if (enemyBullet.y > GameHeight) {
                enemyBulletIterator.remove();
            }
        }

        BackgroundY += 3;
        backgroundY2 += 3;
        backgroundY3+=3;
        combined +=3;
        // Reset the background positions if they go beyond the visible area
        if (combined >= GameHeight*2) {
            BackgroundY = 0;
            backgroundY2 = 0; 
            backgroundY3 = 0;
            combined=0;
        }
        
        checkCollisions();
        frameCount++;
        lastUpdateTime = currentTime;
        repaint();
    }

    private void checkCollisions() {
        Iterator<Rectangle> bulletIterator = Bullets.iterator();
        while (bulletIterator.hasNext()) {
            Rectangle bullet = bulletIterator.next();

            Iterator<EnemyPlane> enemyIterator = enemyPlanes.iterator();
            while (enemyIterator.hasNext()) {
                EnemyPlane enemyPlane = enemyIterator.next();

                if (bullet.intersects(enemyPlane.getX(), enemyPlane.getY(), enemyPlane.getWidth(),
                        enemyPlane.getHeight())) {
                    bulletIterator.remove();
                    enemyIterator.remove();
                    score += 10; // Increase the score by 10 when an enemy plane is destroyed
                }
            }
        }

        Iterator<Rectangle> enemyBulletIterator = EnemyBullets.iterator();
        while (enemyBulletIterator.hasNext()) {
            Rectangle enemyBullet = enemyBulletIterator.next();

            // Check collision with the player's plane
            if (enemyBullet.intersects(planeX, planeY, 50, 50)) {
                enemyBulletIterator.remove();
                Health--; // Decrease health when hit
                int healthBarWidth = (int) ((double) Health / 5.0 * 100.0);
                HealthBar.setSize(healthBarWidth, 20);

            }
            Iterator<EnemyPlane> enemyIterator = enemyPlanes.iterator();
            while (enemyIterator.hasNext()) {
                EnemyPlane enemyPlane = enemyIterator.next();

                if (collisionWithUserPlane(enemyPlane.getX(), enemyPlane.getY(), enemyPlane.getWidth(),
                        enemyPlane.getHeight())) {
                    enemyIterator.remove();
                    Health--; // Decrease health when an enemy plane collides with the user plane
                    int healthBarWidth = (int) ((double) Health / 5.0 * 100.0);
                    HealthBar.setSize(healthBarWidth, 20);
                }
            }
        }

        //Update the scoreLabel text when the score changes
        scoreLabel.setText("Score: " + score);

        //Update the healthLabel text when the health changes
        //healthLabel.setText("Health: " + Health);

        //Game over condition
        if (Health <= 0) {
            JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score, "Game Over" ,JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private boolean collisionWithUserPlane(int x, int y, int width, int height) {
        Rectangle userPlaneBounds = new Rectangle(planeX, planeY, 50, 50);
        Rectangle enemyPlaneBounds = new Rectangle(x, y, width, height);

        return userPlaneBounds.intersects(enemyPlaneBounds);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Move the character based on the arrow keys
        if (key == KeyEvent.VK_UP) {
            planeY -= 10; // Move character up
        } else if (key == KeyEvent.VK_DOWN) {
            planeY += 10; // Move character down
        } else if (key == KeyEvent.VK_LEFT) {
            planeX -= 10; // Move character left
        } else if (key == KeyEvent.VK_RIGHT) {
            planeX += 10; // Move character right
        } else if (key == KeyEvent.VK_SPACE && bulletCooldown <= 0) {
            // Shoot a bullet
            Rectangle bullet1 = new Rectangle(planeX + 35, planeY, 10, 10);
            Bullets.add(bullet1);
            bulletCooldown = MAX_BULLET_COOLDOWN;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameLogic();
        if (bulletCooldown > 0) {
            bulletCooldown--;
        }
    }
}