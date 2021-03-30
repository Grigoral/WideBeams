package ru.wideBeams;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

	 private JFrame frame;
	    private JPanel pane;

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new Launcher().createAndShowGui();
	            }
	        });
	    }

	    public void createAndShowGui() {
	        frame = new JFrame(getClass().getSimpleName());
	        pane = new JPanel() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                
	                Graphics2D g2d = (Graphics2D)g;
	                
	                
	                List<int[]> listCircle = new ArrayList<int[]>();
	                List<int[]> listRectangle = new ArrayList<int[]>();
	                double max=0;
	                int p=10;
	                int t=200;
	                for(int i=0;i<p;i++) {
	                	int[] temp1 = new int[3];
	                	temp1[0]=(int) (Math.random() * t) ; temp1[1]=(int) (Math.random() * t) ;temp1[2]=(int) (Math.random() * t);
	                	listCircle.add(temp1);
	                }
	                
	                for(int i=0;i<p;i++) {
	                	int[] temp2 = new int[4];temp2[0]=(int) (Math.random() * t) ;temp2[1]=(int) (Math.random() * t) ;temp2[2]=5000;temp2[3]=(int) (Math.random() * t) ;
	                	listRectangle.add(temp2);
	                }
	                for(int i=0;i<p;i++) {
	                	Rectangle test = new Rectangle((int)listRectangle.get(i)[0], (int)listRectangle.get(i)[1], (int)listRectangle.get(i)[2],  (int)listRectangle.get(i)[3]);
		                g2d.draw(test);
		                
		                drawCenteredCircle(g2d,(int)listCircle.get(i)[0], (int)listCircle.get(i)[1], (int)listCircle.get(i)[2] );
	                }
	                
	                int totalI=-1,totalJ=-1;
	           
	                for(int i=0; i<listRectangle.size(); i++) {
	                	for(int j=0; j<listCircle.size(); j++) {
	                	
	                	
	                	int	rectangleX1 =listRectangle.get(i)[0];
	                	int	rectangleY1 =listRectangle.get(i)[1];
	                	//int	rectangleWidth =listRectangle.get(i)[2];
	                	int	rectangleHeight =listRectangle.get(i)[3];
	                	int	x0 =listCircle.get(j)[0];
	                	int	y0 =listCircle.get(j)[1];
	                	int	radius =listCircle.get(j)[2]/2;
//	                	Rectangle test1 = new Rectangle((int)listRectangle.get(i)[0], 
//	                			(int)listRectangle.get(i)[1], 
//	                			(int)listRectangle.get(i)[2], 
//	                			(int)listRectangle.get(i)[3]);
	                	
	                	if(x0-radius>=rectangleX1) {//центр минус  радиус правее короткой стороны прямоугольника
	                	
	                		// 1. если центр окружности ниже нижней стороны треугольника и вершина окружности внутри прямоугольника
	                		if(y0>=rectangleY1+rectangleHeight
	                				&& y0-radius>=rectangleY1) {
	                			if(squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)>max) {
	                				max= squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//2. если центр окружности выше верхей стороны треугольника и вершина окружности внутри прямоугольника
	                		if(y0<=rectangleY1
	                				&& y0+radius<=rectangleY1 +rectangleHeight 
	                				) {
	                			if(Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1, true)>max) {
	                				max=Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1, true);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//3. если центр окружности внутри прямоугольника и ближе к вержней стороне
	                		if(y0<=rectangleY1+rectangleHeight && y0>=rectangleY1
	                				&& (2*rectangleY1 +rectangleHeight)/2>y0) {
	                			if(Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1, true)>max) {
	                				max=Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1, true);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//4. если центр окружности внутри прямоугольника и ближе к нижней стороне
	                		if(y0<=rectangleY1+rectangleHeight && y0>=rectangleY1
	                				&& (2*rectangleY1 +rectangleHeight)/2<y0) {
	                			if(Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)>max) {
	                				max=Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//5. центр внутри прямоугольника, но окружжность вылазиет с двух сторон
	                		if(y0<=rectangleY1+rectangleHeight && y0>=rectangleY1
	                				&& y0-radius<rectangleY1 && y0+radius>rectangleY1) {
	                			if(Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)- squareSegment(radius, x0,y0,rectangleY1, true)>max) {
	                				max=Math.PI*radius*radius- squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)- squareSegment(radius, x0,y0,rectangleY1, true);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                			//6. окружность целиком внутри прямоугольника
	                		if(y0<=rectangleY1+rectangleHeight && y0>=rectangleY1
	                				&& y0-radius>=rectangleY1 && y0+radius<=rectangleY1) {
	                			if(Math.PI*radius*radius>max) {
	                				max=Math.PI*radius*radius;
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                			}
	                	 }
	                	
	                	//если центр лежит левее вертикальной стороны угла, центр плю радиус правее
	                	if(x0<rectangleX1 && x0+radius>rectangleX1) {
	                		
	                		//7. центр лежит выше верхней стороны, а центр плюс радиус между сторонами 
	                		if(y0<rectangleY1 && y0+radius>rectangleY1 
	                				&& y0+radius<=rectangleY1+rectangleHeight) {
	                			           if(squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
	                			        	   max=squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			        	     totalI=i; 
	                				                totalJ=j;
	                							}
	                		}
	                		//8. центр лежит ниже нижней стороны, а центр плюс радиус между сторонами 
	                		if(y0>rectangleY1+rectangleHeight && y0-radius>rectangleY1 
	                				&& y0-radius<=rectangleY1+rectangleHeight) {
	                			           if(squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
	                			        	   max=squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			        	     totalI=i; 
	                				                totalJ=j;
	                							}
	                		}
	                		//9. Центр выше верхней, а центр плюс радиус ниже нижней
	                		if(y0<rectangleY1 && y0+radius>rectangleY1+rectangleHeight) {
         			           if(squareSector(radius,x0,y0,rectangleX1,rectangleY1)-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
        			        	   max=squareSector(radius,x0,y0,rectangleX1,rectangleY1)-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight);
        			        	     totalI=i; 
        				                totalJ=j;
        							}
	                		} 
	                		//10. Центр ниже нижней, а центр минус радиус выше верхней
	                		if(y0>rectangleY1+rectangleHeight && y0-radius<rectangleY1) {
         			           if(squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)
         			        		   -squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
        			        	   max=squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)-
        			        			   squareSector(radius,x0,y0,rectangleX1,rectangleY1);
        			        	     totalI=i; 
        				                totalJ=j;
        							}
	                		} 
	                		//11. центр между бесконечными сторонами, окружность пересекает только короткую сторону
	                		if(y0-radius>=rectangleY1 && y0+radius<=rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleX1, false)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleX1, false);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//12. центр между бесконечными сторонами, окружность пересекает только длинные стороны
	                		if(y0>=rectangleY1 && y0<=rectangleY1+rectangleHeight
	                				&& y0-radius<rectangleY1 && y0+radius>rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleX1, false)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleX1, false)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//13. центр между бесконечными сторонами, окружность пересекает нижнюю и короткую
	                		if(y0>=rectangleY1 && y0<=rectangleY1+rectangleHeight
	                				&& y0-radius>rectangleY1 && y0+radius>rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleX1, false)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleX1, false)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//14. центр между бесконечными сторонами, окружность пересекает верхнюю и короткую
	                		if(y0>=rectangleY1 && y0<=rectangleY1+rectangleHeight
	                				&& y0-radius<rectangleY1 && y0+radius<rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleX1, false)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleX1, false)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                	
	                	
	                	}
	                	if(x0>=rectangleX1 && x0-radius<rectangleX1) {//если центр лежит правее вертикальной стороны угла, центр минус радиус левее
	                		//15 и 19. центр выше верхней, окружность пересекает (может касаться нижней) верхнюю и короткую
	                		if(y0<rectangleY1
	                				&& y0+radius>rectangleY1 && y0+radius<=rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleX1+rectangleHeight, true)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		
	                		//16 и 20. центр ниже нижней, окружность пересекает (может касаться верхней) нижнюю и короткую
	                		if(y0>rectangleY1
	                				&& y0-radius>rectangleY1 && y0-radius<=rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleX1+rectangleHeight, true)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                	
	                		//17 и 21. центр выше верхней, окружность пересекает обе длинные
	                		if(y0<rectangleY1
	                				&&  y0+radius<rectangleY1+rectangleHeight) {
	                			if(squareSegment(radius, x0,y0,rectangleY1, true)-
	                					squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)
	                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleY1, true)-
		                					squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1)
		                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		
	                		//18 и 22. центр ниже нижней, окружность пересекает обе длинные
	                		if(y0>rectangleY1+rectangleHeight
	                				&&  y0-radius<rectangleY1) {
	                			if(squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)-
	                					squareSegment(radius, x0,y0,rectangleY1, true)
	                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)
	                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1)>max) {
	                				max=squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)-
		                					squareSegment(radius, x0,y0,rectangleY1, true)
		                					-squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)
		                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                	
	                		//23, 24, 25 и 26. центр между длинными, окружность пересекает длинные и может персечь короткую
	                		if(y0>=rectangleY1 && y0<=rectangleY1+rectangleHeight
	                				&&  y0+radius>=rectangleY1+rectangleHeight &&  y0-radius<=rectangleY1+rectangleHeight) {
	                			if(Math.PI*radius*radius-  squareSegment(radius, x0,y0,rectangleY1, true)
	                					-  squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
	                					-  squareSegment(radius, x0,y0,rectangleX1, false)
	                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1)
	                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
	                				max=Math.PI*radius*radius-  squareSegment(radius, x0,y0,rectangleY1, true)
		                					-  squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
		                					-  squareSegment(radius, x0,y0,rectangleX1, false)
		                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1)
		                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//27, 29 и 32. Центр между длинными, окружность пересекает или касается верхней (нижней) и пересекает короткую
	                		if(y0>rectangleY1 && y0<rectangleY1+rectangleHeight 
	                				&& y0+radius<=rectangleY1+rectangleHeight && y0-radius<=rectangleY1+rectangleHeight) {
	                			if(Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1, true)
	                					-  squareSegment(radius, x0,y0,rectangleX1, false)>max) {
	                				max=Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1, true)
		                					-  squareSegment(radius, x0,y0,rectangleX1, false);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//28 и 33. Центр между длинными, окружность пересекает короткую и нижнюю (может касаться)
	                		if(y0>rectangleY1 && y0<rectangleY1+rectangleHeight 
	                				&& y0+radius>=rectangleY1+rectangleHeight && y0-radius>rectangleY1+rectangleHeight) {
	                			if(Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
	                					-  squareSegment(radius, x0,y0,rectangleX1, false)>max) {
	                				max=Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
		                					-  squareSegment(radius, x0,y0,rectangleX1, false);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//30. Центр между длинными, окружность пересекает короткую и нижнюю и касается верхней
	                		if(y0>rectangleY1 && y0<rectangleY1+rectangleHeight 
	                				&& y0+radius>rectangleY1+rectangleHeight && y0-radius==rectangleY1) {
	                			if(Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
	                					-  squareSegment(radius, x0,y0,rectangleX1, false)
	                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
	                				max=Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
		                					-  squareSegment(radius, x0,y0,rectangleX1, false)
		                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                		//31. Центр между длинными, окружность пересекает короткую и верхнюю и касается нижней
	                		if(y0>rectangleY1 && y0<rectangleY1+rectangleHeight 
	                				&& y0+radius==rectangleY1+rectangleHeight && y0-radius<rectangleY1) {
	                			if(Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1, true)
	                					-  squareSegment(radius, x0,y0,rectangleX1, false)
	                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1+rectangleHeight)>max) {
	                				max=Math.PI*radius*radius-squareSegment(radius, x0,y0,rectangleY1+rectangleHeight, true)
		                					-  squareSegment(radius, x0,y0,rectangleX1, false)
		                					+squareSector(radius,x0,y0,rectangleX1,rectangleY1);
	                			     totalI=i; 
	             	                totalJ=j;
	             				}
	                		}
	                	}
	                  }
	                }
	                if(totalI>-1 && totalJ>-1) {
	                Rectangle test = new Rectangle((int)listRectangle.get(totalI)[0], 
	                		(int)listRectangle.get(totalI)[1], 
	                		(int)listRectangle.get(totalI)[2], 
	                		(int)listRectangle.get(totalI)[3]);
	                
	                Area a1 = new Area(test);
	                Area a2 = new Area(new Ellipse2D.Double((int)listCircle.get(totalJ)[0], (int)listCircle.get(totalJ)[1], (int)listCircle.get(totalJ)[2], (int)listCircle.get(totalJ)[2]));

	                a1.subtract(a2);
	                g2d.fill(a1);
	                }
	                g.dispose();
	            }

	            @Override
	            public Dimension getPreferredSize() {
	                return new Dimension(1200, 600);
	            }
	        };
	        frame.add(pane);
	        frame.pack();
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	    
	    public  boolean intersect(Rectangle r, double CircleCenterX, double CircleCenterY, double radius )
	    {
	        double cx = Math.abs(CircleCenterX - r.x - r.getWidth()/2);
	        double xDist = r.getWidth()/2 + radius;
	        if (cx > xDist)
	            return false;
	        double cy = Math.abs(CircleCenterY - r.y - r.getWidth()/2);
	        double yDist = r.getWidth()/2 + radius;
	        if (cy > yDist)
	            return false;
	        if (cx <= r.getWidth()/2 || cy <= r.getWidth()/2)
	            return true;
	        double xCornerDist = cx - r.getWidth()/2;
	        double yCornerDist = cy - r.getWidth()/2;
	        double xCornerDistSq = xCornerDist * xCornerDist;
	        double yCornerDistSq = yCornerDist * yCornerDist;
	        double maxCornerDistSq = radius * radius;
	        return xCornerDistSq + yCornerDistSq <= maxCornerDistSq;
	    }
	    
	    public void drawCenteredCircle(Graphics2D g, int x0, int y0, int diameter) {
	    	  x0 = x0-diameter/2;
	    	  y0 = y0-diameter/2;
	    	  g.drawOval(x0,y0,diameter,diameter);
	    	}
	    
	    public void drawFillCenteredCircle(Graphics2D g, int x0, int y0, int diameter) {
	    	  x0 = x0-diameter/2;
	    	  y0 = y0-diameter/2;
	    	  g.fillOval(x0,y0,diameter,diameter);
	    	}
	    
	    //находим площадь  сегмента
	    public double squareSegment(int radius, int  x0, int y0, int rectangleSideCoord, boolean horizontal) {
	    	//находим точки пересечения  стороны прямоугольника и круга
	    	double[] array=new double[2];
	    	if(horizontal) {
	    		array[0]= x0+Math.sqrt(radius*radius-(rectangleSideCoord-y0)*(rectangleSideCoord-y0));
	    		array[1]  =      x0-Math.sqrt(radius*radius-(rectangleSideCoord-y0)*(rectangleSideCoord-y0));
	    	}
	    	else {
	    	array[0]= y0+Math.sqrt(radius*radius-(rectangleSideCoord-x0)*(rectangleSideCoord-x0));
	    	array[1]= y0-Math.sqrt(radius*radius-(rectangleSideCoord-x0)*(rectangleSideCoord-x0));
	    	}
	    	
	    	double center = (array[0]+array[1])/2;
	    	
	    	double sinA=(array[0]-center)/radius;
	    	
	    	double square=0.5*(Math.asin(sinA)-sinA)*radius*radius;
	    	
	    	return square;
	    }
	    
	    public double squareSector(int radius, int x0, int y0, int x1, int y1) {
	    	
	    	double x2=0;
	    	double y2=0;
	    	//находим точки персечения с окружностью
	    	if(x1>x0 && y1<y0) {
	    		x2=x0+Math.sqrt((int)(radius*radius*4-(y1-y0)*(y1-y0)));
	    		y2=y0+Math.sqrt((int)(radius*radius*4-(x1-x0)*(x1-x0)));
	    	}
	    	
	    	if(x1<x0 && y1<y0) {
	    		x2=x0-Math.sqrt((int)(radius*radius*4-(y1-y0)*(y1-y0)));
	    		y2=y0+Math.sqrt((int)(radius*radius*4-(x1-x0)*(x1-x0)));
	    	}
	    	
	    	if(x1<x0 && y1>y0 ) {
	    		x2=x0-Math.sqrt((int)(radius*radius*4-(y1-y0)*(y1-y0)));
	    		y2=y0-Math.sqrt((int)(radius*radius*4-(x1-x0)*(x1-x0)));
	    	}
	    	
	    	if(x1>x0 && y1>y0) {
	    		x2=x0+Math.sqrt((int)(radius*4*radius-(y1-y0)*(y1-y0)));
	    		y2=y0-Math.sqrt((int)(radius*4*radius-(x1-x0)*(x1-x0)));
	    	}
	    	double centerX=(x2+x1)/2;
	    	double centerY=(y2+y1)/2;
	    	double sinA=0;
	    	sinA=Math.sqrt((x2-centerX)*(x2-centerX)+(y2-centerY)*(y2-centerY))/radius;
	    	//площадь сектора будет площадь сегмента плюс площадь треугольника
	    	double square=0.5*(Math.asin(sinA)-sinA)*radius*radius+0.5*Math.abs(y2-y1)*Math.abs(x2-x1);
	    	return square;
	    }
	    
	    public void drawRectangle(Graphics2D g, int x1, int y1, int x2, int y2) {
	    	 
	    	Rectangle rect2 = new Rectangle(x1, y1, x2, y2);
	    	g.draw(rect2);
	    	}
}
/* int r1=250, r2=80,r3=300,r4=200;
int q1=200, q2=250,q3=200;
Rectangle yourLine = new Rectangle(r1,  r2, r3,  r4);
   g2d.draw(yourLine);
drawCenteredCircle(g2d, q1, q2, q3);
listCircle.add(new double[] {q1,q2,q3});
listRectangle.add(new double[] {r1,  r2, r3,  r4});
*/