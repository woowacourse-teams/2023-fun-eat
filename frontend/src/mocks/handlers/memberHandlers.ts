import { rest } from 'msw';

export const memberHandlers = [
  rest.get('/api/members', (req, res, ctx) => {
    const { mockSessionId } = req.cookies;

    if (mockSessionId === 'abc123') {
      return res(ctx.status(200), ctx.json({ nickname: '냐미', profileImage: '' }));
    }

    if (mockSessionId === 'qwe456') {
      return res(ctx.status(200), ctx.json({ nickname: '뇨미', profileImage: '' }));
    }

    return res(ctx.status(403));
  }),
];
