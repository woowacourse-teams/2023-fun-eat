import { Badge, useTheme } from '@fun-eat/design-system';
import styled from 'styled-components';

interface ReviewTagItemProps {
  id: number;
  content: string;
  isSelected: boolean;
  isDisabled: boolean;
  toggleTagSelection: (id: number, isSelected: boolean) => void;
}

const ReviewTagItem = ({ id, content, isSelected, isDisabled, toggleTagSelection }: ReviewTagItemProps) => {
  const theme = useTheme();

  return (
    <TagSelectButton onClick={() => toggleTagSelection(id, isSelected)} disabled={isDisabled}>
      <Badge
        color={theme.colors.primary}
        textColor={theme.textColors.default}
        css={isSelected && `font-weight: ${theme.fontWeights.bold}`}
      >
        {content}
      </Badge>
    </TagSelectButton>
  );
};

export default ReviewTagItem;

const TagSelectButton = styled.button`
  &:disabled {
    opacity: 0.5;
  }
`;
