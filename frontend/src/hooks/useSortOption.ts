import { useState } from 'react';

const useSortOption = (initialOption: string) => {
  const [selectedOption, setSelectedOption] = useState(initialOption);

  const selectSortOption = (selectedOptionLabel: string) => {
    setSelectedOption(selectedOptionLabel);
  };

  return { selectedOption, selectSortOption };
};

export default useSortOption;
