import { useState } from 'react';

import type { SortOption } from '@/types/common';

const useSortOption = (initialOption: SortOption) => {
  const [selectedOption, setSelectedOption] = useState(initialOption);

  const selectSortOption = (sortOption: SortOption) => {
    setSelectedOption(sortOption);
  };

  return { selectedOption, selectSortOption };
};

export default useSortOption;
