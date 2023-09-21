import { createGlobalStyle } from 'styled-components';

import fonts from './font';

const GlobalStyle = createGlobalStyle`
${fonts}

  #root {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
`;

export default GlobalStyle;
