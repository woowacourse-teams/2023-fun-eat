import { useState } from 'react';

const useStarRating = () => {
  const [rating, setRating] = useState(0);
  const [hovering, setHovering] = useState(0);

  const handleRating = (starIndex: number) => {
    setRating(starIndex);
  };

  const handleMouseEnter = (starIndex: number) => {
    setHovering(starIndex);
  };

  const handleMouseLeave = () => {
    setHovering(0);
  };

  return { rating, hovering, handleRating, handleMouseEnter, handleMouseLeave };
};

export default useStarRating;
