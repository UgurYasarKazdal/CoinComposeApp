package org.example.project.core.database.portfolio

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.portfolio.data.local.PortfolioCoinEntity
import org.example.project.portfolio.data.local.PortfolioDao
import org.example.project.portfolio.data.local.UserBalanceDao
import org.example.project.portfolio.data.local.UserBalanceEntity


@Database(entities = [PortfolioCoinEntity::class, UserBalanceEntity::class], version = 1)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
    abstract fun userBalanceDao(): UserBalanceDao
}
