import { Badge, Button, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

import { useReviewFormActionContext } from '@/hooks/context';
import { convertTagColor } from '@/utils/converTagColor';

interface ReviewTagItemProps {
  id: number;
  name: string;
  variant: string;
  isSelected: boolean;
}

const ReviewTagItem = ({ id, name, variant, isSelected }: ReviewTagItemProps) => {
  const { handleReviewFormValue } = useReviewFormActionContext();
  const theme = useTheme();

  const tagColor = convertTagColor(variant);

  const handleReviewTag = () => {
    handleReviewFormValue({ target: 'tagIds', value: id, isSelected });
  };

  return (
    <Button type="button" weight="bold" variant="transparent" onClick={handleReviewTag}>
      <TagBadge
        isSelected={isSelected}
        tagColor={tagColor}
        size="sm"
        color="transparent"
        textColor={theme.textColors.default}
        role={'radio'}
        aria-label={`${name} 태그`}
        aria-checked={isSelected}
      >
        {name}
      </TagBadge>
    </Button>
  );
};

export default ReviewTagItem;

const TagBadge = styled(Badge)<{ tagColor: string; isSelected: boolean }>`
  border: 2px solid ${({ tagColor }) => tagColor};
  background: ${({ tagColor, isSelected }) => isSelected && tagColor};
  white-space: nowrap;
`;
