:-multifile(entity/3).

entity('webpro.model.WebproAddress', compound,
	[
		key(id, integer),
		field(street, atom),
		field(city, atom),
		field(state, atom),
		field(zip, atom),
		field(country, atom)
	]
).

entity('webpro.model.WebproPerson', compound,
	[
		key(id, integer),
		field(firstName, atom),
		field(middleName, atom),
		field(lastName, atom),
		field(idnumber, atom),
		field(address, atom),
		field(phone, atom),
		field(email, atom),
		field(photo, atom),
		field(company, atom),
		field(salt, atom),
		field(user, atom),
		field(pwd, atom),
		field(loginCount, integer),
		field(weight, float),
		field(heigth, float)
	]
).

entity('webpro.model.WebproDepartment', compound,
	[
		key(id, integer),
		field(name, atom)
	]
).