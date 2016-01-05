//
//  GameState.h
//  DotsAndBoxes
//
//  Created by student on 12/9/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GridState : NSObject
//Easier if we just add it as points
-(void) addLine:(NSUInteger)xs xEnd:(NSUInteger)xe yStart:(NSUInteger)ys yEnd:(NSUInteger)ye;
-(NSMutableArray*) getUpdatedGrid;
@end
