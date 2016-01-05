//
//  PlayerVC.m
//  DotsAndBoxes
//
//  Created by student on 12/7/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "PlayerOneVC.h"
#import "PlayerTwoVC.h"
@interface PlayerOneVC () //<UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UITextField *nameTextField;
@property (weak, nonatomic) IBOutlet UIButton *nextButton;
@property (weak, nonatomic) IBOutlet UISegmentedControl *colorSelection;

@end

@implementation PlayerOneVC

- (void)viewDidLoad {
    [super viewDidLoad];
    self.nextButton.enabled = false;
    // Do any additional setup after loading the view.
  //  self.nameTextField.delegate = self;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



- (IBAction)editingChanged:(id)sender {
    if([self.nameTextField.text length] != 0){
        self.nextButton.enabled = true;
    }
    else{
        self.nextButton.enabled = false;
    }
}


#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    //Make playerTwo aware of playerOne choices
    PlayerTwoVC* playerTwoVC = (PlayerTwoVC*)segue.destinationViewController;
    [playerTwoVC setPlayerOneInfo:self.nameTextField.text colorIdx:self.colorSelection.selectedSegmentIndex];
    
}


@end
