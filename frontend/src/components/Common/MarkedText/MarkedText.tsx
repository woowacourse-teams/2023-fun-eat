import { Fragment } from 'react';
import styled from 'styled-components';

interface MarkedTextProps {
  text: string;
  mark: string;
}

const MarkedText = ({ text, mark }: MarkedTextProps) => {
  const textFragments = text.split(new RegExp(`(${mark})`, 'gi'));

  return (
    <>
      {textFragments.map((fragment, index) => (
        <Fragment key={`fragment-${index}`}>
          {fragment.toLowerCase() === mark.toLowerCase() ? <Mark>{fragment}</Mark> : <>{fragment}</>}
        </Fragment>
      ))}
    </>
  );
};

export default MarkedText;

const Mark = styled.mark`
  font-weight: ${({ theme }) => theme.fontWeights.bold};
  background-color: ${({ theme }) => theme.backgroundColors.default};
`;
