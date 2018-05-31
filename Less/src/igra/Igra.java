package igra;

import java.util.LinkedList;
//test

public class Igra {
	//bo povezala vse skupaj, tukaj bo konstruktor za zacetek igre, ra�unanje potez
	//doloci final dimenzijo plosce, recimo 6
	
	/**
	 * Velikost igralne plo��e
	 */
	public static final int DIM = 6;
	
	//ATRIBUTI: 
	protected Igralec naPotezi;
	protected Plosca igralnaPlosca;
	protected int kvotaPremikov; // vsak igralec ima na voljo po 3 poteze vsaki� ko je na vrsti
	protected Stanje trenutnoStanje; //spremlja trenutno stanje
	protected int zmagovalnaKvota; //ko beli zmaga si zapomnimo, v koliko premikih je koncal zadnjo potezo
	
	public Igra() {
		naPotezi = Igralec.BELI;
		kvotaPremikov = 3; 
		igralnaPlosca = new Plosca(DIM);
		zmagovalnaKvota = 0;
		trenutnoStanje = Stanje.BELI_NA_POTEZI;	
	}
	
	/**
	 *
	 * @param igra nova kopija dane igre
	 */

	public Igra(Igra igra) {
		int dim = igra.DIM;
		this.naPotezi = igra.getNaPotezi();
		this.kvotaPremikov = igra.getKvotaPremikov();
		this.zmagovalnaKvota = igra.getZmagovalnaKvota();
		this.trenutnoStanje = igra.getTrenutnoStanje();
		Polje[][] kopijaVsehPolj = new Polje[dim][dim];
		int[][] kopijaNavpicnih = new int[dim][dim];
		int[][] kopijaVodoravnih = new int[dim][dim];
		Polje[][] vsaPolja = igra.getIgralnaPlosca().getVsa_polja();
		int[][] navpicne = igra.getIgralnaPlosca().getOgrajiceNavp();
		int[][] vodoravne = igra.getIgralnaPlosca().getOgrajiceVod();
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				kopijaVsehPolj[i][j] = vsaPolja[i][j];
				kopijaNavpicnih[i][j] = navpicne[i][j];
				kopijaVodoravnih[i][j] = vodoravne[i][j];
			}
		}
		
		Plosca kopijaPlosca = new Plosca(dim);
		kopijaPlosca.setOgrajiceNavp(kopijaNavpicnih);
		kopijaPlosca.setOgrajiceVod(kopijaVodoravnih);
		kopijaPlosca.setVsaPolja(kopijaVsehPolj);
		this.igralnaPlosca = kopijaPlosca;
	}
		
	/**
	 * 
	 * @param x - stolpec v katerem se nahaja figurica za katero preverjamo mo�ne poteze.
	 * @param y - vrstica v katerem se nahaja figurica za katero preverjamo mo�ne poteze.
	 * @param kvota - kvota potez igralca, ne moremo poklicati, ce ima igralec kvoto 0.
	 * @return vse mo�ne poteze izbrane figurice
	 */
	public LinkedList<Lokacija> moznePoteze(Lokacija p, int kvota) {  //gledamo mozne poteze za eno figurico
		int x = p.getX();
		int y = p.getY();
		LinkedList<Lokacija> poteze = new LinkedList<Lokacija>();
		//Tekmovalec se lahko premakne za eno mesto dol, gor, levo, desno, 
		//za dve mesti dol, gor, levo, desno (le v primeru, ko preskoci  oviro). 
		//Preveriti je potrebno se ce igralec poskusi preskocit. 
		
		//ta del se izvede, tudi v primeru ko imamo na voljo �e 3 kvote, saj lahko porabimo le eno. 
		if (kvota >= 1) {
			//premik levo za eno mesto: x koordinata se zmanj�a za 1, pogledamo v tabelo navpi�nih ograjic
			//na mesto x. 
			
			//preverimo, da ne pademo iz plosce.
			if ( x-1 >= 0) {
				//preverimo, ce je polje prazno
				if (igralnaPlosca.vsaPolja[y][x-1] == Polje.PRAZNO) {
					//preverimo, ce nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceNavp[y][x] == 0) {
						//ce vse to velja, je to veljavna poteza.
						poteze.add(new Lokacija(x-1,y));
					}
				}
			}
			//premik desno za eno mesto: x koordinata se pove�a za 1, pogledamo v tabelo navpi�nih ograjic
			//na mesto x + 1. 
			
			//preverimo, da ne pademo iz plosce.
			if ( x+1 < DIM) {
				//preverimo, ce je polje prazno
				if (igralnaPlosca.vsaPolja[y][x+1] == Polje.PRAZNO) {
					//preverimo, ce nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceNavp[y][x+1] == 0) {
						//ce vse to velja, je to veljavna poteza.
						poteze.add(new Lokacija(x+1,y));
					}
				}
			}
			//premik gor za eno mesto: y koordinata se pomanjsa za 1, pogledamo v tabelo vodoravnih ograjic
			//na mesto y.
			
			//preverimo, da ne pademo iz plosce.
			if ( y-1 >= 0) {
				//preverimo, ce je polje prazno
				if (igralnaPlosca.vsaPolja[y-1][x] == Polje.PRAZNO) {
					//preverimo, ce nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceVod[y][x] == 0) {
						//ce vse to velja, je to veljavna poteza.
						poteze.add(new Lokacija(x,y-1));
					}
				}	
			}
			
			//premik dol za eno mesto: y koordinata se poveca za 1, pogledamo v tabelo vodoravnih ograjic
			//na mesto y+1.
			
			//preverimo, da ne pademo iz plosce.
			if ( y+1 < DIM) {
				//preverimo, ce je polje prazno
				if (igralnaPlosca.vsaPolja[y+1][x] == Polje.PRAZNO) {
					//preverimo, ce  nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceVod[y+1][x] == 0) {
						//ce vse to velja, je to veljavna poteza.
						poteze.add(new Lokacija(x,y+1));
					}
				}	
			}
		
			//Premik za 2 mesti - preskok  cez drugo figurico. 
			
			//Premik levo za dve mesti: x koordinata se bo zmanj�ala za 2,
			//Ko preskakujemo figurice, ne smemo imeti nikjer ograjice, zato pogledamo v tabelo 
			//navpi�nih ograjc, na mesto x-1 in x
			 
			//preskok levo
			if (x-2 >= 0) {
				if (igralnaPlosca.vsaPolja[y][x-2] == Polje.PRAZNO) {
					//dejansko preskocimo nekoga
					if(igralnaPlosca.vsaPolja[y][x-1] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceNavp[y][x-1] == 0 && igralnaPlosca.ograjiceNavp[y][x]==0) {
							poteze.add(new Lokacija(x-2,y));
						}
					}
				}
			}
			
			//Preskok desno
			if (x+2 < DIM) {
				if (igralnaPlosca.vsaPolja[y][x+2] == Polje.PRAZNO) {
					//dejansko preskocimo nekoga
					if(igralnaPlosca.vsaPolja[y][x+1] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceNavp[y][x+1] == 0 && igralnaPlosca.ograjiceNavp[y][x+2]==0) {
							poteze.add(new Lokacija(x+2,y));
						}
					}
				}
			}
			
			//Preskok gor 
			if (y-2 >= 0) {
				if (igralnaPlosca.vsaPolja[y-2][x] == Polje.PRAZNO) {
					//dejansko preskocimo nekoga
					if(igralnaPlosca.vsaPolja[y-1][x] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceVod[y-1][x] == 0 && igralnaPlosca.ograjiceVod[y][x]==0) {
							poteze.add(new Lokacija(x,y-2));
						}
					}
				}
			}
			
			//Preskok dol
			if (y+2 < DIM) {
				if (igralnaPlosca.vsaPolja[y+2][x] == Polje.PRAZNO) {
					//dejansko preskocimo nekoga
					if(igralnaPlosca.vsaPolja[y+1][x] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceVod[y+1][x] == 0 && igralnaPlosca.ograjiceVod[y+2][x]==0) {
							poteze.add(new Lokacija(x,y+2));
						}
					}
				}
			}
			
		}
		
		
		if (kvota >= 2) {
			//preveriti moramo samo premikanje za eno mesto levo, desno, gor, dol z eno ograjo. 
			
			//premik levo: 
			if ( x-1 >= 0) {
				if (igralnaPlosca.vsaPolja[y][x-1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x] == 1) {
						poteze.add(new Lokacija(x-1,y));
					}
				}
			}
			
			//premik desno: 
			if ( x+1 < DIM) {
				if (igralnaPlosca.vsaPolja[y][x+1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x+1] == 1) {
						poteze.add(new Lokacija(x+1,y));
					}
				}
			}
			
			//premik gor: 
			if ( y-1 >= 0) {
				if (igralnaPlosca.vsaPolja[y-1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y][x] == 1) {
						poteze.add(new Lokacija(x,y-1));
					}
				}	
			}
			
			//premik dol: 
			if ( y+1 < DIM) {
				if (igralnaPlosca.vsaPolja[y+1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y+1][x] == 1) {
						poteze.add(new Lokacija(x,y+1));
					}
				}	
			}
						
		}
		if (kvota == 3) {
			//preveriti je potrebno premikanje za eno mesto levo, desno, gor, dol z dvema ograjama. 
			//premik levo: 
			if ( x-1 >= 0) {
				if (igralnaPlosca.vsaPolja[y][x-1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x] == 2) {
						poteze.add(new Lokacija(x-1,y));
					}
				}
			}
			
			//premik desno: 
			if ( x+1 < DIM) {
				if (igralnaPlosca.vsaPolja[y][x+1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x+1] == 2) {
						poteze.add(new Lokacija(x+1,y));
					}
				}
			}
			
			//premik gor: 
			if ( y-1 >= 0) {
				if (igralnaPlosca.vsaPolja[y-1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y][x] == 2) {
						poteze.add(new Lokacija(x,y-1));
					}
				}	
			}
			
			//premik dol: 
			if ( y+1 < DIM) {
				if (igralnaPlosca.vsaPolja[y+1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y+1][x] == 2) {
						poteze.add(new Lokacija(x,y+1));
					}
				}	
			}
					
		}
				
		return poteze;
	}
	/**
	 * 
	 * @param trenutna - trenutna pozicija
	 * @param zeljena - izbrano polje za premik figurice
	 * @return ali lahko ibrano figurico igralec premakne na zeleno polje
	 */
	public boolean veljavnaPoteza(Lokacija trenutna, Lokacija zelena) {
		// Preveriti potrebno  se, ce je trenutna lokacija sploh os trenutnega igralca polje
		Polje polje = igralnaPlosca.vsaPolja[trenutna.getY()][trenutna.getX()];
		if (Polje.BELO == polje && naPotezi != Igralec.BELI) {
			return false;
		} else if(Polje.CRNO == polje && naPotezi != Igralec.CRNI) {
			return false;
		} else if (Polje.PRAZNO == polje) {
			return false;
		}
		LinkedList<Lokacija> mozne = moznePoteze(trenutna, kvotaPremikov);
		return mozne.contains(zelena);
	}
	
	
	/**
	 * Naredi potezo: spremeni kvoto, po potrebi spremeni igralca, 
	 * prestavi figurico, ceje poteza veljavna, pove�a stevilo potez.
	 * @param trenutna
	 * @param koncna
	 */
	public void narediPotezo(Lokacija trenutna, Lokacija koncna) {  //tu se pogoji nekako ponavljajo enako kot so napisani zgoraj - kako bi to lahko bilo bolje?
		if (veljavnaPoteza(trenutna,koncna) == false) {
			//System.out.println("Neveljaven premik. Poskusi znova.");
		} else {
			//Prestavimo figurico
			igralnaPlosca.vsaPolja[koncna.getY()][koncna.getX()] = igralnaPlosca.vsaPolja[trenutna.getY()][trenutna.getX()];
			igralnaPlosca.vsaPolja[trenutna.getY()][trenutna.getX()] = Polje.PRAZNO;
			
			//Izra�un koliko kvote je porabil. 	mozne napake: napacni indeksi ograjic
			if (Math.abs(trenutna.getX() - koncna.getX()) == 2 || Math.abs(trenutna.getY() - koncna.getY()) == 2) { //presko�imo figurico
				kvotaPremikov = kvotaPremikov - 1;
			} else {
				if (trenutna.getX() < koncna.getX() ) {
					//premik v desno: 
					int kvota = 1 + igralnaPlosca.ograjiceNavp[trenutna.getY()][koncna.getX()];
					kvotaPremikov = kvotaPremikov - kvota;
				} else if ( trenutna.getX() > koncna.getX()) {
					//premik v levo: 
					int kvota = 1 + igralnaPlosca.ograjiceNavp[trenutna.getY()][trenutna.getX()];
					kvotaPremikov = kvotaPremikov - kvota;
				} else if (trenutna.getY() > koncna.getY() ) {
					//premik gor: 
					int kvota = 1 + igralnaPlosca.ograjiceVod[trenutna.getY()][trenutna.getX()];
					kvotaPremikov = kvotaPremikov - kvota;
				} else if(trenutna.getY() < koncna.getY()) {
					//premik dol:
					int kvota = 1 + igralnaPlosca.ograjiceVod[koncna.getY()][koncna.getX()];
					kvotaPremikov = kvotaPremikov - kvota;
				}
			}
			
			
			//Spremenimo igralca, �e je potrebno
			if (kvotaPremikov == 0) {
				naPotezi = naPotezi.nasprotnik();
				kvotaPremikov = 3; 
				trenutnoStanje = trenutnoStanje.zamenjaj();
			}
		
		} 
		
		//Preverimo �e je igre konec.
		boolean konecCrni = igralnaPlosca.konecCrni();
		boolean konecBeli = igralnaPlosca.konecBeli();
		if (konecCrni && !konecBeli) { 
			trenutnoStanje = Stanje.ZMAGA_CRNI;
		} else if (konecBeli) {							//to je potrebno narediti, ker lahko beli kon�a �e preden porabi tri korake. 
			if (naPotezi != Igralec.CRNI) {                 //ce je crni na potezi, potem je beli koncal s kvoto==0, torej je kvota crnega ze pravilno nastavljena na 3
				naPotezi = Igralec.CRNI;
				zmagovalnaKvota = 3 - kvotaPremikov;
				kvotaPremikov = 3;		
			}
			if (konecCrni) {
				if (kvotaPremikov < zmagovalnaKvota) {
					trenutnoStanje = Stanje.ZMAGA_CRNI;
				} else if (kvotaPremikov == zmagovalnaKvota){
					trenutnoStanje = Stanje.NEODLOCENO;					
				} else {
					trenutnoStanje = Stanje.ZMAGA_BELI;
				}
			} else {
				trenutnoStanje = Stanje.ZMAGA_BELI;
			}
		} 
	}

	public Plosca getIgralnaPlosca() {
		return igralnaPlosca;
	}

	public Stanje getTrenutnoStanje() {
		return trenutnoStanje;
	}

	public void setTrenutnoStanje(Stanje trenutnoStanje) {
		this.trenutnoStanje = trenutnoStanje;
	}

	public int getKvotaPremikov() {
		return kvotaPremikov;
	}

	public void setKvotaPremikov(int kvotaPremikov) {
		this.kvotaPremikov = kvotaPremikov;
	}

	public Igralec getNaPotezi() {
		return naPotezi;
	}

	public void setNaPotezi(Igralec naPotezi) {
		this.naPotezi = naPotezi;
	}

	public int getZmagovalnaKvota() {
		return zmagovalnaKvota;
	}

	public void setZmagovalnaKvota(int zmagovalnaKvota) {
		this.zmagovalnaKvota = zmagovalnaKvota;
	}


	public static int getDim() {
		return DIM;
	}

	public void setIgralnaPlosca(Plosca igralnaPlosca) {
		this.igralnaPlosca = igralnaPlosca;
	}
	
	public int[][] getOgrajiceVod(Plosca igralnaPlosca){
		return igralnaPlosca.getOgrajiceVod();
	}
	
	public int[][] getOgrajiceNavp(Plosca igralnaPlosca){
		return igralnaPlosca.getOgrajiceNavp();
	}

}
