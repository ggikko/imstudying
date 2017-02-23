import unittest


def insertion_sort(input_list):
    for index, input_value in enumerate(input_list):
        holePosition = index
        while 0 < holePosition and input_list[holePosition - 1] > input_value:
            input_list[holePosition - 1], input_list[holePosition] = input_list[holePosition], input_list[holePosition - 1]
            holePosition -= 1

    return input_list


class unit_test(unittest.TestCase):
    def test(self):
        self.assertEqual(insertion_sort([1, 3, 5, 4, 2, 7]), [1, 2, 3, 4, 5, 7])
        self.assertEqual(insertion_sort([4, 6, 1, 3, 5, 7, 2, 7, 8]), [1, 2, 3, 4, 5, 6, 7, 7, 8])
