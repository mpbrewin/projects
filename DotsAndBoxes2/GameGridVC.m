//
//  GameGridVC.m
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "GameGridVC.h"
#import "GameGridView.h"
#import "GameLineView.h"
#import "Line.h"
#import "GridState.h"
#define x0 39
#define x1 149
#define x2 262
#define x3 371
#define y0 103
#define y1 213
#define y2 324
#define y3 433
@interface GameGridVC ()
//Game Players
@property(strong,nonatomic) NSMutableArray* players; //Will be size 2, but allows for effecient turn swapping
@property(readwrite) NSUInteger turn;

//Drawing Info
@property(readwrite,nonatomic) NSUInteger xStart;
@property(readwrite,nonatomic) NSUInteger yStart;
@property(readwrite,nonatomic) NSUInteger xEnd;
@property(readwrite,nonatomic) NSUInteger yEnd;

//GameGrid state
@property(strong,nonatomic) GridState* gridState;
@property(strong,nonatomic) NSMutableArray* oldGrid;

//Separate the drawings into two different views for effeciency
//Draws the dot grid and all of the previous lines
@property (weak, nonatomic) IBOutlet GameGridView *gameGridView;
//Draws the current line in real time
@property (weak, nonatomic) IBOutlet GameLineView *gameLineView;

@property (weak, nonatomic) IBOutlet UILabel *playerOneName;

@property (weak, nonatomic) IBOutlet UILabel *playerTwoName;

@end

@implementation GameGridVC

- (void)viewDidLoad {
    [super viewDidLoad];
    //Create the gridState
    self.gridState = [[GridState alloc] init];
    self.oldGrid = [[NSMutableArray alloc] initWithCapacity:3]; //Array will include lines and spaces (3x3)
    for(int i = 0; i < 3; i++){
        self.oldGrid[i] = [[NSMutableArray alloc] initWithCapacity:3];
        for(int j = 0; j < 3; j++){
            self.oldGrid[i][j] = @(0);
        }
    }

    self.playerOneName.text = [[self.players[0] getName] stringByAppendingString:@" 0"];
    self.playerTwoName.text = [[self.players[1] getName] stringByAppendingString:@" 0"];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//Setup//
-(void) setPlayers:(Player *)p1 Player2:(Player *)p2{
    self.players = [[NSMutableArray alloc] initWithObjects:p1, p2, nil];
    self.playerOneName.text = [p1 getName];
    self.playerTwoName.text = [p2 getName];
    self.turn = 0;

}

//Drawing logic//
-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    //Send the player's color
    [self.gameLineView setColor:[self.players[self.turn] r]green:[self.players[self.turn] g] blue:[self.players[self.turn] b]];
    [self.gameGridView setColor:[self.players[0] r]green:[self.players[0] g] blue:[self.players[0] b] red2:[self.players[1] r] green2:[self.players[1] g] blue2:[self.players[1] b]];
    
    //Setup touch gesture
    UITouch* touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self.gameLineView];
    //Determine starting points
    if(touchPoint.x <= x0 + 40 && touchPoint.x >= 0)
        self.xStart = x0;
    else if(touchPoint.x <= x1 + 40 && touchPoint.x >= x1 - 40)
        self.xStart = x1;
    else if(touchPoint.x < x2 + 40 && touchPoint.x >= x2 - 40)
        self.xStart = x2;
    else if(touchPoint.x < x3 + 40 && touchPoint.x >= x3 - 40)
        self.xStart = x3;
    
    if(touchPoint.y <= y0 + 40 && touchPoint.y >= y0 - 40)
        self.yStart = y0;
    else if(touchPoint.y <= y1 + 40 && touchPoint.y >= y1 - 40)
        self.yStart = y1;
    else if(touchPoint.y < y2 + 40 && touchPoint.y >= y2 - 40)
        self.yStart = y2;
    else if(touchPoint.y < y3 + 40 && touchPoint.y >= y3 - 40)
        self.yStart = y3;
}
//Update drawing points in real time
-(void)touchesMoved:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    //Setup touch gesture
    UITouch* touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self.gameLineView];
    //Determine the ending points
    self.xEnd = touchPoint.x;
    self.yEnd = touchPoint.y;
    
    //Send these points to the gameLineView to draw
    [self.gameLineView setPoints:self.xStart xEnd:self.xEnd yStart:self.yStart yEnd:self.yEnd];
    //Tell the view to draw/update the context
    [self.gameLineView setNeedsDisplay];
    
    
}

//This will automatically snap the drawn line to the correct points
//Also sends the newest line to the gameGridView so it can add it to its model
-(void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    //Setup touch gesture
    UITouch* touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self.gameLineView];
    //Determine ending points
    if(touchPoint.x <= x0 + 40 && touchPoint.x >= 0)
        self.xEnd = x0;
    else if(touchPoint.x <= x1 + 40 && touchPoint.x >= x1 - 40)
        self.xEnd = x1;
    else if(touchPoint.x < x2 + 40 && touchPoint.x >= x2 - 40)
        self.xEnd = x2;
    else if(touchPoint.x < x3 + 40 && touchPoint.x >= x3 - 40)
        self.xEnd = x3;
    
    if(touchPoint.y <= y0 + 40 && touchPoint.y >= y0 - 40)
        self.yEnd = y0;
    else if(touchPoint.y <= y1 + 40 && touchPoint.y >= y1 - 40)
        self.yEnd = y1;
    else if(touchPoint.y < y2 + 40 && touchPoint.y >= y2 - 40)
        self.yEnd = y2;
    else if(touchPoint.y < y3 + 40 && touchPoint.y >= y3 - 40)
        self.yEnd = y3;
    
    //Send these finalized points to the gameLineView to draw
    [self.gameLineView setPoints:self.xStart xEnd:self.xEnd yStart:self.yStart yEnd:self.yEnd];
    
    //Tell the view to draw/update the context
    [self.gameLineView setNeedsDisplay];
    
    //Also send the line to the gameGridView
    [self.gameGridView addLine:[[Line alloc] initWithxStart:self.xStart-1 xEnd:self.xEnd-1 yStart:self.yStart+32 yEnd:self.yEnd+32]];
    
    //Add the physical representation to the internal gridState
    [self.gridState addLine:self.xStart xEnd:self.xEnd yStart:self.yStart yEnd:self.yEnd];
    
    //Check for any new completed boxes and update UI as needed
    [self checkForCompletedBoxes];
    
    //Is the game over?
    if([self gameIsOver]){
        //Determine winner

        if([self.players[0] getScore] > [self.players[1] getScore]){
            
        }
        else if([self.players[1] getScore] > [self.players[0] getScore])
            
        //Display an alert displaying winner info
        NSString* winner = [self.players[self.turn] getName];
        winner = [winner stringByAppendingString:@" won!"];
        UIAlertController* gameOverText = [UIAlertController alertControllerWithTitle:@"Game Over!" message: winner preferredStyle: UIAlertControllerStyleAlert];
        UIAlertAction* okAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
            //Take them back to the main menu
            [self performSegueWithIdentifier:@"MainMenu" sender:self];
        }];
        [gameOverText addAction:okAction];
        [self presentViewController:gameOverText animated:YES completion:nil];
    }
    
    //Next players turn
    self.turn = (self.turn+1)%2;
}


-(void) checkForCompletedBoxes{
    NSMutableArray* newGrid = [self.gridState getUpdatedGrid];
    NSLog(@"%@", newGrid);
    //Compare the oldGrid with the newGrid for any new boxes
    for(int i = 0; i < 3; i++){
        for(int j=0; j < 3; j++){
            int a = [self.oldGrid[i][j] intValue];
            int b = [newGrid[i][j] intValue];
            //A box is completed
            if(a != b && b == 4) {
                //Update the player's score and scoreLabel
                [self.players[self.turn] addPoint];
                NSString* val = @" ";
                val = [val stringByAppendingString: [NSString stringWithFormat:@"%tu", [self.players[self.turn]getScore]]];
                if(self.turn == 0){
                    self.playerOneName.text = [[self.players[0] getName] stringByAppendingString:val];
                }
                else{
                    self.playerTwoName.text = [[self.players[0] getName] stringByAppendingString:val];
                }
            }
            //Update old grid
            self.oldGrid[i][j] = @(b);
        }
    }
    // NSLog(@"\n");
}

//Check to see if all the boxes have been completed
-(BOOL) gameIsOver{
    for(int i = 0; i < 3; i++){
        for(int j=0; j < 3; j++){
            int a = [self.oldGrid[i][j] intValue];
            if(a != 4){
                return NO;
            }
        }
    }
    return YES;
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
