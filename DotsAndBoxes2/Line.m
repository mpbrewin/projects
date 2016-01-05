//
//  Line.m
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "Line.h"
@interface Line()
@property (readwrite, nonatomic) NSUInteger xStart;
@property (readwrite, nonatomic) NSUInteger xEnd;
@property (readwrite, nonatomic) NSUInteger yStart;
@property (readwrite, nonatomic) NSUInteger yEnd;
@end
@implementation Line
-(id)initWithxStart:(NSUInteger)xs xEnd:(NSUInteger)xe yStart:(NSUInteger)ys yEnd:(NSUInteger)ye{
    if(self = [super init]){
        self.xStart = xs;
        self.xEnd = xe;
        self.yStart = ys;
        self.yEnd = ye;
    }
    return self;
}

-(NSUInteger) xStart{
    return _xStart;
}

-(NSUInteger) xEnd{
    return _xEnd;
}

-(NSUInteger) yStart{
    return _yStart;
}

-(NSUInteger) yEnd{
    return _yEnd;
}

@end
