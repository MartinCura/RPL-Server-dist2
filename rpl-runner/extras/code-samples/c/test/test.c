#include <criterion/criterion.h>
#include "solution.c"

Test(misc, failing) {
    cr_assert(test_method_1());
}

Test(misc, passing) {
    cr_assert(test_method_1());
}
