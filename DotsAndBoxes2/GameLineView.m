//
//  GameLineView.m
//  DotsAndBoxes
//
//  Created by student on 12/8/15.
//  Copyright © 2015 Max Brewin. All rights reserved.
//

#import "GameLineView.h"
#import "GameGridVC.h"
#import "Line.h"
#define x0 39
#define x1 149
#define x2 262
#define x3 371
#define y0 103
#define y1 213
#define y2 324
#define y3 433

@interface GameLineView()
@property(readwrite,nonatomic) NSUInteger xStart;
@property(readwrite,nonatomic) NSUInteger yStart;
@property(readwrite,nonatomic) NSUInteger xEnd;
@property(readwrite,nonatomic) NSUInteger yEnd;
//p1 color
@property(readwrite,nonatomic) CGFloat r1;
@property(readwrite,nonatomic) CGFloat g1;
@property(readwrite,nonatomic) CGFloat b1;
//p2 color
@property(readwrite,nonatomic) CGFloat r2;
@property(readwrite,nonatomic) CGFloat g2;
@property(readwrite,nonatomic) CGFloat b2;
@end
@implementation GameLineView


- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.xStart = 0;
        self.yStart = 0;
        self.xEnd = 0;
        self.yEnd = 0;
       // self.clearsContextBeforeDrawing = NO;
    }
    return self;
}

// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetRGBStrokeColor(context, self.r1, self.g1, self.b2, 1.0);
    CGContextSetLineWidth(context, 2.0);
    CGContextMoveToPoint(context, self.xStart, self.yStart);
    CGContextAddLineToPoint(context, self.xEnd, self.yEnd);
    CGContextStrokePath(context);
}

-(void) setPoints:(NSUInteger)xs xEnd:(NSUInteger)xe yStart:(NSUInteger)ys yEnd:(NSUInteger)ye{
    self.xStart = xs;
    self.xEnd = xe;
    self.yStart = ys;
    self.yEnd = ye;
}

-(void) setColor:(NSNumber*)r green:(NSNumber*)g blue:(NSNumber*)b{
    self.r1 = [r floatValue];
    self.g1 = [g floatValue];
    self.b1 = [b floatValue];
}


/*
-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    //Setup touch gesture
    UITouch* touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self];
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

-(void)touchesMoved:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    //Setup touch gesture
    UITouch* touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self];
    //Determine the ending points
    self.xEnd = touchPoint.x;
    self.yEnd = touchPoint.y;
    [self setNeedsDisplayInRect:CGRectMake(self.xStart, self.yStart, self.xEnd, self.yEnd)];
    
}

-(void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    //Setup touch gesture
    UITouch* touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self];
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
    
   // UIViewController * myController = [self firstAvailableUIViewController];
    //Find the vc
   // GameGridVC * vc = (GameGridVC*)[self firstAvailableUIViewController];
    //Send the line to the vc so it can forward it to the GameGridVC
    //The GameGridVC will draw all previous lines so we aren;t redrawing the existing grid everytime
    //we are in this method
    //[vc addLine:[[Line alloc] initWithxStart:self.xStart xEnd:self.xEnd yStart:self.yStart yEnd:self.yEnd]];
 
    //We will draw this ine though
    [self setNeedsDisplayInRect:CGRectMake(self.xStart, self.yStart, self.xEnd, self.yEnd)];
    
    
}

//For finding the vc
- (UIViewController *) firstAvailableUIViewController {
    // convenience function for casting and to "mask" the recursive function
    return [self traverseResponderChainForUIViewController];
}

- (id) traverseResponderChainForUIViewController {
    id nextResponder = [self nextResponder];
    if ([nextResponder isKindOfClass:[UIViewController class]]) {
        return nextResponder;
    } else if ([nextResponder isKindOfClass:[UIView class]]) {
        return [nextResponder traverseResponderChainForUIViewController];
    } else {
        return nil;
    }
}
 */



@end
