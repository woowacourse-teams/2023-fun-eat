import { useState } from 'react';

const useStarRatingHover = () => {
  const [hovering, setHovering] = useState(0);

  const handleMouseEnter = (starIndex: number) => {
    setHovering(starIndex);
  };

  const handleMouseLeave = () => {
    setHovering(0);
  };

  return { hovering, handleMouseEnter, handleMouseLeave };
};

export default useStarRatingHover;
