package org.gerdoc.dao.impl;

import org.gerdoc.dao.AlumnoDao;
import org.gerdoc.model.Alumno;
import org.gerdoc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AlumnoDaoImpl implements AlumnoDao
{
    private Session session;

    public AlumnoDaoImpl( )
    {
        session = HibernateUtil
                .getSessionFactory( )
                .openSession( );
    }

    @Override
    public List<Alumno> findAll()
    {
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Alumno> criteria =
                builder.createQuery(Alumno.class);
        criteria.from(Alumno.class);
        return session
                .createQuery(criteria)
                .getResultList();
    }

    @Override
    public Alumno findById(Long id)
    {

        return session.get( Alumno.class, id );
    }

    @Override
    public Alumno save(Alumno alumno)
    {
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            session.save( alumno );
            transaction.commit( );
            return alumno;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            transaction.rollback( );
            return null;
        }
    }

    @Override
    public void deleteById(Long id)
    {
        Transaction transaction = null;
        Alumno alumno = null;
        try
        {
            alumno = findById( id );
            if( alumno != null )
            {
                transaction = session.beginTransaction();
                session.delete( alumno );
                transaction.commit( );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            transaction.rollback( );
        }
    }

    @Override
    public Alumno update(Alumno alumno)
    {
        Transaction transaction = null;
        Alumno updatedAlumno = null;
        try
        {
            transaction = session.beginTransaction();
            updatedAlumno = (Alumno) session.merge( alumno );
            transaction.commit( );
            return updatedAlumno;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            transaction.rollback( );
            return null;
        }
    }
}
