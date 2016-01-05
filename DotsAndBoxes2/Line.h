//
//  Line.h
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Line : NSObject
-(id)initWithxStart:(NSUInteger)xs xEnd:(NSUInteger)xe yStart:(NSUInteger)ys yEnd:(NSUInteger)ye;
- (NSUInteger) xStart;
- (NSUInteger) xEnd;
- (NSUInteger) yStart;
- (NSUInteger) yEnd;
@end
