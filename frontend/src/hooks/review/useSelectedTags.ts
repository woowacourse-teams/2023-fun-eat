import { useState } from 'react';

const useSelectedTags = (minDisplayedTagsLength: number) => {
  const [selectedTags, setSelectedTags] = useState<number[]>([]);

  const toggleTagSelection = (id: number, isSelected: boolean) => {
    if (selectedTags.length >= minDisplayedTagsLength && !isSelected) {
      return;
    }

    setSelectedTags((prevSelectedTags) => {
      if (isSelected) {
        return prevSelectedTags.filter((selectedTag) => selectedTag !== id);
      }
      return [...prevSelectedTags, id];
    });
  };

  return { selectedTags, setSelectedTags, toggleTagSelection };
};

export default useSelectedTags;
