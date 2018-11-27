export interface IDishes {
    id?: string;
    menuId?: string;
    dishName?: string;
    dishType?: string;
    dishPrice?: number;
}

export class Dishes implements IDishes {
    constructor(
        public id?: string,
        public menuId?: string,
        public dishName?: string,
        public dishType?: string,
        public dishPrice?: number
    ) {}
}
