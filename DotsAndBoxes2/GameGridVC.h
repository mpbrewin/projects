//
//  GameGridVC.h
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Line.h"
#import "Player.h"
@interface GameGridVC : UIViewController
- (void) setPlayers:(Player*) p1 Player2:(Player*) p2;
//- (void) addLine: (Line*) l;
@end
