
public class LevelControl {
	public static void level(){
	if(Board.gamelevel==1){
		Board.length=5000;
		Board.manager = new EnemyM(20,Board.length, 1000);
		Board.manager1 = new EnemyM1(1,Board.length,2000);
		Board.manager2 = new EnemyM2(0,Board.length,80000);
		Board.manager3 = new EnemyM3(0,Board.length,25000);
		Board.manager4 = new EnemyM4(0,Board.length,25000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,100,1200);
		}
	if(Board.gamelevel==2){
		Board.length=10000;
		Board.manager = new EnemyM(40,Board.length, 1000);
		Board.manager1 = new EnemyM1(1,Board.length,2000);
		Board.manager2 = new EnemyM2(0,Board.length,80000);
		Board.manager3 = new EnemyM3(15,Board.length,4000);
		Board.manager4 = new EnemyM4(0,Board.length,25000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==3){
		Board.length=20000;
		Board.manager = new EnemyM(75,Board.length, 1000);
		Board.manager1 = new EnemyM1(3,Board.length,2000);
		Board.manager2 = new EnemyM2(10,Board.length,25000);
		Board.manager3 = new EnemyM3(40,Board.length,8000);
		Board.manager4 = new EnemyM4(0,Board.length,000);
		Board.manager5 = new EnemyM5(0,Board.length,0000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==4){
		Board.length=35000;
		Board.manager = new EnemyM(200,Board.length, 1000);
		Board.manager1 = new EnemyM1(8,Board.length,2000);
		Board.manager2 = new EnemyM2(30,Board.length,40000);
		Board.manager3 = new EnemyM3(100,Board.length,8000);
		Board.manager4 = new EnemyM4(8,Board.length,20000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==5){
		Board.length=50000;
		Board.manager = new EnemyM(250,Board.length, 1000);
		Board.manager1 = new EnemyM1(12,Board.length,5000);
		Board.manager2 = new EnemyM2(45,Board.length,80000);
		Board.manager3 = new EnemyM3(125,Board.length,25000);
		Board.manager4 = new EnemyM4(22,Board.length,25000);
		Board.manager5 = new EnemyM5(3,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==6){
		Board.length=80000;
		Board.manager = new EnemyM(200,Board.length, 1000);
		Board.manager1 = new EnemyM1(20,Board.length,5000);
		Board.manager2 = new EnemyM2(30,Board.length,80000);
		Board.manager3 = new EnemyM3(65,Board.length,25000);
		Board.manager4 = new EnemyM4(20,Board.length,25000);
		Board.manager5 = new EnemyM5(10,Board.length,10000);
		Board.manager6 = new EnemyM6(250,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==7){
		Board.length=100000;
		Board.manager = new EnemyM(200,Board.length, 1000);
		Board.manager1 = new EnemyM1(20,Board.length,5000);
		Board.manager2 = new EnemyM2(30,Board.length,80000);
		Board.manager3 = new EnemyM3(65,Board.length,25000);
		Board.manager4 = new EnemyM4(20,Board.length,25000);
		Board.manager5 = new EnemyM5(10,Board.length,10000);
		Board.manager6 = new EnemyM6(250,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==8){
		Board.length=125000;
		Board.manager = new EnemyM(200,Board.length, 1000);
		Board.manager1 = new EnemyM1(20,Board.length,5000);
		Board.manager2 = new EnemyM2(30,Board.length,80000);
		Board.manager3 = new EnemyM3(65,Board.length,25000);
		Board.manager4 = new EnemyM4(20,Board.length,25000);
		Board.manager5 = new EnemyM5(10,Board.length,10000);
		Board.manager6 = new EnemyM6(250,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==9){
		Board.length=160000;
		Board.manager = new EnemyM(200,Board.length, 1000);
		Board.manager1 = new EnemyM1(20,Board.length,5000);
		Board.manager2 = new EnemyM2(30,Board.length,80000);
		Board.manager3 = new EnemyM3(65,Board.length,25000);
		Board.manager4 = new EnemyM4(20,Board.length,25000);
		Board.manager5 = new EnemyM5(10,Board.length,10000);
		Board.manager6 = new EnemyM6(250,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==10){
		Board.length=7500;
		Board.manager = new EnemyM(0,Board.length, 1000);
		Board.manager1 = new EnemyM1(0,Board.length,5000);
		Board.manager2 = new EnemyM2(100,Board.length+15000,3000);
		Board.manager3 = new EnemyM3(0,Board.length,25000);
		Board.manager4 = new EnemyM4(0,Board.length,25000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==11){
		Board.length=12000;
		Board.manager = new EnemyM(0,Board.length, 1000);
		Board.manager1 = new EnemyM1(0,Board.length,5000);
		Board.manager2 = new EnemyM2(0,Board.length,80000);
		Board.manager3 = new EnemyM3(0,Board.length,25000);
		Board.manager4 = new EnemyM4(0,Board.length,25000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(800,30000,1000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==12){
		Board.length=10000;
		Board.manager = new EnemyM(0,Board.length, 1000);
		Board.manager1 = new EnemyM1(0,Board.length,5000);
		Board.manager2 = new EnemyM2(120,Board.length+25000,4000);
		Board.manager3 = new EnemyM3(0,Board.length,25000);
		Board.manager4 = new EnemyM4(50,Board.length+10000,1000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==13){
		Board.length=10000;
		Board.manager = new EnemyM(0,Board.length, 1000);
		Board.manager1 = new EnemyM1(0,Board.length,5000);
		Board.manager2 = new EnemyM2(0,Board.length,80000);
		Board.manager3 = new EnemyM3(200,Board.length+10000,1000);
		Board.manager4 = new EnemyM4(20,Board.length,1500);
		Board.manager5 = new EnemyM5(15,Board.length,1000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(0,15000,98000);
		}
	if(Board.gamelevel==14){
		Board.length=99999;
		Board.manager = new EnemyM(0,Board.length, 1000);
		Board.manager1 = new EnemyM1(0,Board.length,5000);
		Board.manager2 = new EnemyM2(200,Board.length,8000);
		Board.manager3 = new EnemyM3(0,Board.length,25000);
		Board.manager4 = new EnemyM4(0,Board.length,25000);
		Board.manager5 = new EnemyM5(30,Board.length,1000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(1,1,1200);
		}
	if(Board.gamelevel==15){
		Board.length=999999;
		Board.manager = new EnemyM(0,Board.length, 1000);
		Board.manager1 = new EnemyM1(0,Board.length,5000);
		Board.manager2 = new EnemyM2(0,Board.length,80000);
		Board.manager3 = new EnemyM3(0,Board.length,25000);
		Board.manager4 = new EnemyM4(0,Board.length,25000);
		Board.manager5 = new EnemyM5(0,Board.length,10000);
		Board.manager6 = new EnemyM6(0,15000,98000);
		Board.managerBoss = new EnemyMBoss(1,1,1200);
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
}
