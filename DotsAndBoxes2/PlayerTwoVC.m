//
//  PlayerTwoVC.m
//  DotsAndBoxes
//
//  Created by student on 12/7/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "PlayerTwoVC.h"
#import "Player.h"
#import "GameGridVC.h"
@interface PlayerTwoVC ()
//Player1 info
@property (strong,nonatomic) NSString* p1Name;
@property (readwrite,nonatomic) NSUInteger p1ColorIdx;
//Player2 info
@property (weak, nonatomic) IBOutlet UITextField *nameTextField;
@property (weak, nonatomic) IBOutlet UIButton *nextButton;
@property (weak, nonatomic) IBOutlet UISegmentedControl *colorSelection;
@property BOOL enteredValidName;
@property BOOL pickedValidColor;
@end

@implementation PlayerTwoVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.nextButton.enabled = false;
    self.enteredValidName = NO;
    if(self.p1ColorIdx == 0){
        self.pickedValidColor = NO;
    }
    else{
        self.pickedValidColor = YES;
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
//Gets called in PlayerOneVC prepareForSegue
-(void)setPlayerOneInfo:(NSString*)name colorIdx:(NSUInteger)idx{
    self.p1Name = name;
    self.p1ColorIdx = idx;
}

//changed text
- (IBAction)editingChanged:(id)sender {
    if([self.nameTextField.text length] != 0){
        self.enteredValidName = YES;
        if(self.pickedValidColor == YES)
            self.nextButton.enabled = true;
    }
    else{
        self.nextButton.enabled = false;
    }
}

- (IBAction)changedColor:(id)sender {
    if(self.colorSelection.selectedSegmentIndex != self.p1ColorIdx){
        self.pickedValidColor = YES;
        if(self.enteredValidName == YES)
            self.nextButton.enabled = true;
    }
    else{
        self.nextButton.enabled = false;
    }
}



#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    //Create the players and pass them to the GameGridVC
    Player* player1 = [[Player alloc] initWithName:self.p1Name colorChoice:self.p1ColorIdx];
    Player* player2 = [[Player alloc] initWithName:self.nameTextField.text colorChoice:self.colorSelection.selectedSegmentIndex];
    GameGridVC* gameGridVC = (GameGridVC*)segue.destinationViewController;
    [gameGridVC setPlayers:player1 Player2:player2];
}


@end
