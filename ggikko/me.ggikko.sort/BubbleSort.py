import unittest


def bubble_sort(input_list):
    list_length = len(input_list) - 1

    for i in range(list_length):
        for j in range(list_length):
            if input_list[j] > input_list[j + 1]:
                input_list[j], input_list[j + 1] = input_list[j + 1], input_list[j]
    return input_list


class unit_test(unittest.TestCase):
    def test(self):
        self.assertEqual(bubble_sort([1, 3, 5, 4, 2, 7]), [1, 2, 3, 4, 5, 7])
        self.assertEqual(bubble_sort([4, 6, 1, 3, 5, 7, 2, 7, 8]), [1, 2, 3, 4, 5, 6, 7, 7, 8])
