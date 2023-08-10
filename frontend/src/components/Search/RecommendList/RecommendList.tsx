import { Text } from '@fun-eat/design-system';
import styled from 'styled-components';

const MOCK_RECOMMENDS = [
  '삼립)담곰이꿀데니쉬',
  '삼립)담곰이꿀',
  '삼립)담곰이꿀데',
  '삼립)담곰이꿀데니',
  '삼립)담곰이꿀데니쉬이',
];

const RecommendList = () => {
  return (
    <RecommendListContainer>
      {MOCK_RECOMMENDS.map((recommend) => (
        <li key={recommend}>
          <RecommendText>{recommend}</RecommendText>
        </li>
      ))}
    </RecommendListContainer>
  );
};

export default RecommendList;

const RecommendListContainer = styled.ul`
  height: fit-content;
  max-height: 150px;
  padding: 10px 0;
  border: 1px solid ${({ theme }) => theme.borderColors.default};
  overflow-y: auto;

  & > li {
    height: 36px;
    line-height: 36px;
    padding: 0 10px;
  }
`;

const RecommendText = styled(Text)`
  line-height: 36px;
`;
