//
//  GameGridView.h
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Line.h"
@interface GameGridView : UIView
-(void) setColor:(NSNumber*)r green:(NSNumber*)g blue:(NSNumber*)b red2:(NSNumber*)r2 green2:(NSNumber*)g2 blue2:(NSNumber*)b2;
- (void) addLine: (Line*) l;
@end
