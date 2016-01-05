//
//  GameLineView.h
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GameLineView : UIView

-(void) setPoints:(NSUInteger)xs xEnd:(NSUInteger)xe yStart:(NSUInteger)ys yEnd:(NSUInteger)ye;
-(void) setColor:(NSNumber*)r green:(NSNumber*)g blue:(NSNumber*)b;
//- (UIViewController *) firstAvailableUIViewController;
//- (id) traverseResponderChainForUIViewController;
@end
