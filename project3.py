#!/usr/bin/env python3
"""
The *bean machine*, also known as the *Galton Board* or *quincunx*, is a device invented by Sir Francis Galton to demonstrate the central limit theorem, in particular that the normal distribution is approximate to the binomial distribution.
"""

# I did the functions but I couldn't figure out how to use threading. So i am not quite sure if my code it working.

import argparse
import random
import threading


class Board:
    """
    Class Board
    
    Contains multiple bins that collect beans
    Contains multiple levels of pegs
    """

    def __init__(self, bins: int):
        """Make a new board of the specified size"""
        self._bins = [0] * bins
        self._pegs = bins // 2

    def status(self, pos: int):
        """Print status"""
		print(self._bins.run())

    def __len__(self):
        """Return the board size"""
        return len(self._bins)

    def __getitem__(self, idx: int):
        """Get number of beans in the specified bin"""
        return self._bins[idx]

    def __setitem__(self, idx: int, new_value: int):
        """Set number of beans in the specified bin"""
        self._bins[idx] = new_value

    @property
    def pegs(self):
        """Return number of levels of pegs"""
        return self._pegs


class Bean(threading.Thread):
    """
    Class Bean
    Data members: board, current position, probability, lock
    """

    def __init__(self, board: object, start: int, prob: float, lock: object):
        """Make a new Bean"""
		self.board = board
		self._bin = start
		# probability is always 50%
		self.prob = .5
		self.lock = lock

    def move_left(self):
        """Move a bean left"""
		# has to go left on the bean machine
        self.board._bins[self._bin] -= 1
		self.board._bins[self._bin - 1] += 1
		self._bin -= 1
		return None

    def move_right(self):
        """Move a bean right"""
		# has to go right on the bean machine
		self.board._bins[self._bin] += 1
		self.board._bins[self._bin + 1] -= 1
		self._bin += 1
		return None

    def run(self):
        """Run a bean through the pegs"""
		for i in range(self.board.pegs)
		# use random
		left_right = random.random # used random because it would just give a random number
		# if left_right is smaller than the probability go left, if not, go right
		if left_right < self.prob:
			self.move_left
		else:
			self.move_right
		return None


def main():
    """Main function"""
    # Parse command-line arguments
  parser = argparse.ArgumentParser(description="Process the arguments.")
	
	print("Start")
	# Had to add an argument for all of the four things
	parser.add_argument("beans")
	parser.add_argument("bins")
	parser.add_argument("start")
	parser.add_argument("prob")
	args = parser.parse_args()
	# just to see what numbers were added in
	print("Beans: {}, bins: {}, start: {}, prob: {}".format(args.beans,args.bins,args.start,args.prob))
	gameBoard = Board(int(agrs.bins))
	run_bean = Bean((gameBoard.beans),(gameBoard.bins),(gameBoard.start),(gameBoard.prob))
	print(run_bean.run())
  print("Done")


if __name__ == "__main__":
    main()
